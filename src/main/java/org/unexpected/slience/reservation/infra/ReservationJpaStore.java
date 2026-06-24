package org.unexpected.slience.reservation.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.reservation.domain.Reservation;
import org.unexpected.slience.schedule.infra.ScheduleRepository;
import org.unexpected.slience.user.infra.UserRepository;

@RequiredArgsConstructor
@Service
public class ReservationJpaStore implements ReservationStore {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Reservation addReservation(Reservation reservation) {


        return null;
    }
}
