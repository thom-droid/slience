package org.unexpected.slience.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reservation {

    private Long id;
    private Long scheduleId;
    private LocalDateTime reservedAt;
    private Status status;
}
