package org.unexpected.slience.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.reservation.api.request.SaveReservationRequest;
import org.unexpected.slience.reservation.application.exception.InvalidSeatException;
import org.unexpected.slience.reservation.application.exception.ReservationException;
import org.unexpected.slience.reservation.application.exception.SeatUnavailableException;
import org.unexpected.slience.reservation.domain.ReservationEntity;
import org.unexpected.slience.reservation.domain.ReservationScheduleSeatEntity;
import org.unexpected.slience.reservation.domain.SeatAllocationEntity;
import org.unexpected.slience.reservation.domain.Status;
import org.unexpected.slience.reservation.infra.ReservationRepository;
import org.unexpected.slience.schedule.domain.ScheduleSeatEntity;
import org.unexpected.slience.schedule.infra.ScheduleRepository;
import org.unexpected.slience.user.application.exception.NoUserFoundException;
import org.unexpected.slience.user.domain.User;
import org.unexpected.slience.user.domain.UserEntity;
import org.unexpected.slience.user.domain.UserEntityMapper;
import org.unexpected.slience.user.infra.UserRepository;
import org.unexpected.slience.user.infra.UserStore;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    private final int expiration = 3;

    @Transactional(rollbackFor = ReservationException.class)
    public ReservationEntity reserve(SaveReservationRequest request) {

        List<ScheduleSeatEntity> sc = scheduleRepository.findScheduleSeatForUpdate(
                request.movieId(),
                request.scheduleId(),
                request.seatIds()
        );

        // 요청받은 좌석과 db 좌석이 다른 경우
        if (sc.size() != request.seatIds().size()) {
            throw new InvalidSeatException(request.seatIds());
        }

        List<Long> scheduleSeatIds = sc.stream().map(ScheduleSeatEntity::getId).toList();

        if (scheduleRepository.existsAllocatedSeat(scheduleSeatIds)) {
            throw new SeatUnavailableException("Seat already taken");
        }

        // TODO:: user check aop
        UserEntity user = userRepository.findByUsername("thom").orElseThrow(() -> new NoUserFoundException(0L));

        // 예약 없는 경우 예약 진행
        for (ScheduleSeatEntity scheduleSeatEntity : sc) {

            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setUser(user);
            reservationEntity.setStatus(Status.RESERVED);
            reservationEntity.setReservedAt(LocalDateTime.now());
            ReservationScheduleSeatEntity reservationScheduleSeatEntity = new ReservationScheduleSeatEntity();
            SeatAllocationEntity seatAllocationEntity = new SeatAllocationEntity(scheduleSeatEntity, reservationEntity, LocalDateTime.now().plusDays(expiration));
        }

    }
}
