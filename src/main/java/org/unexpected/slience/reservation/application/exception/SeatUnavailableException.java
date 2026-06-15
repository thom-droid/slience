package org.unexpected.slience.reservation.application.exception;

public class SeatUnavailableException extends ReservationException {
    public SeatUnavailableException(String message) {
        super(message);
    }
}
