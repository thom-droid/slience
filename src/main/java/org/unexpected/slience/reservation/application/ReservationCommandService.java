package org.unexpected.slience.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.reservation.application.exception.InvalidSeatException;
import org.unexpected.slience.reservation.application.exception.ReservationException;
import org.unexpected.slience.reservation.application.exception.SeatUnavailableException;
import org.unexpected.slience.reservation.domain.Status;
import org.unexpected.slience.reservation.domain.entity.ReservationEntity;
import org.unexpected.slience.reservation.domain.entity.ReservationScheduleSeatEntity;
import org.unexpected.slience.reservation.domain.entity.SeatAllocationEntity;
import org.unexpected.slience.reservation.infra.ReservationRepository;
import org.unexpected.slience.schedule.domain.entity.ScheduleSeatEntity;
import org.unexpected.slience.schedule.infra.ScheduleRepository;
import org.unexpected.slience.screen.infra.SeatAllocationRepository;
import org.unexpected.slience.user.application.exception.NoUserFoundException;
import org.unexpected.slience.user.domain.UserEntity;
import org.unexpected.slience.user.infra.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SeatAllocationRepository seatAllocationRepository;

    @Transactional(rollbackFor = ReservationException.class)
    public ReservationEntity reserve(Long movieId, Long scheduleId, Collection<Long> seatIds) {

        List<ScheduleSeatEntity> sc = scheduleRepository.findScheduleSeatForUpdate(movieId, scheduleId, seatIds);

        // 요청받은 좌석과 db 좌석이 다른 경우
        if (sc.size() != seatIds.size()) {
            throw new InvalidSeatException(seatIds);
        }

        List<Long> scheduleSeatIds = sc.stream().map(ScheduleSeatEntity::getId).toList();

        if (scheduleRepository.existsAllocatedSeat(scheduleSeatIds)) {
            throw new SeatUnavailableException("Seat already taken");
        }

        // TODO:: user check aop
        UserEntity user = userRepository.findByUsername("thom").orElseThrow(() -> new NoUserFoundException(0L));

        // 예약 없는 경우 예약 진행
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.addUser(user);
        reservationEntity.setStatus(Status.BEFORE_PAYMENT);
        reservationEntity.setCreatedAt(LocalDateTime.now());
        reservationEntity.setExpiresAt(LocalDateTime.now().plusMinutes(15L));

        List<SeatAllocationEntity> list = sc.stream()
                .map(s ->
                        new SeatAllocationEntity(s, reservationEntity, LocalDateTime.now()))
                .toList();

        seatAllocationRepository.saveAll(list);

        List<ReservationScheduleSeatEntity> rssEntityList = sc.stream()
                .map(s -> {
                    ReservationScheduleSeatEntity rss = new ReservationScheduleSeatEntity();
                    rss.setScheduleSeatEntity(s);
                    return rss;
                })
                .toList();

        reservationEntity.setReservationScheduleSeats(rssEntityList);

        return reservationRepository.save(reservationEntity);
    }
}
