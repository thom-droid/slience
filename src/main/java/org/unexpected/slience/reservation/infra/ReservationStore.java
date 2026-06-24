package org.unexpected.slience.reservation.infra;


import org.unexpected.slience.reservation.domain.Reservation;

public interface ReservationStore {

    Reservation addReservation(Reservation reservation);
}
