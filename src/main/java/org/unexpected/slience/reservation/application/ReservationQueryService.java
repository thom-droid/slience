package org.unexpected.slience.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.reservation.infra.ReservationRepository;

@RequiredArgsConstructor
@Service
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

}
