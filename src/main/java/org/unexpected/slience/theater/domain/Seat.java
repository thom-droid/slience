package org.unexpected.slience.theater.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat {
    private Long id;
    private String seatRow;
    private String seatNumber;
}
