package org.unexpected.slience.reservation.application.exception;

import java.util.Arrays;
import java.util.Collection;

public class InvalidSeatException extends ReservationException {
    public InvalidSeatException(String message) {
        super(message);
    }

    public InvalidSeatException(Collection<Long> seatIds) {
        super("invalid seats :: " + Arrays.toString(seatIds.toArray()));
    }
}
