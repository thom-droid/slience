package org.unexpected.slience.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Reservation {

    private Long id;
    private List<Long> reservationSeatId;
    private LocalDateTime reservedAt;
    private Status status;
    private LocalDateTime expiresAt;
}
