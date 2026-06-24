package org.unexpected.slience.reservation.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationScheduleSeat {
    private long id;
    private long reservationId;
    private long scheduleSeatId;
}
