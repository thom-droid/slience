package org.unexpected.slience.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Reservation {

    private static final long BEFORE_PAYMENT_EXPIRATION_MINUTE = 15;

    private Long id;
    private List<ReservationScheduleSeat> reservationScheduleSeats;
    private Long userId;
    private LocalDateTime reservedAt;
    private Status status;
    private LocalDateTime expiresAt;

    public void beforePayment() {
        this.status = Status.BEFORE_PAYMENT;
        this.expiresAt = LocalDateTime.now().plusMinutes(BEFORE_PAYMENT_EXPIRATION_MINUTE);
    }

    public void reserved() {
        this.status = Status.RESERVED;
        this.reservedAt = LocalDateTime.now();
    }

    public void paid() {
        this.status = Status.PAID;
    }

    public void cancelled() {
        this.status = Status.CANCELLED;
    }

}
