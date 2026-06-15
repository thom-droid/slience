package org.unexpected.slience.reservation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.unexpected.slience.schedule.domain.ScheduleSeatEntity;

import java.time.LocalDateTime;


/***
 * 좌석 예약여부
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "seat_allocatios",
        indexes = {
                @Index(
                        name = "idx_seat_allocation_reservation",
                        columnList = "reservation_id"
                ),
                @Index(
                        name = "idx_seat_allocation_expires_at",
                        columnList = "expires_at"
                )
        }
)
public class SeatAllocationEntity {

    @Id
    @Column(name = "schedule_seat_id")
    private Long scheduleSeatId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_seat_id", nullable = false)
    private ScheduleSeatEntity scheduleSeat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public SeatAllocationEntity(ScheduleSeatEntity scheduleSeat,
                                ReservationEntity reservation,
                                LocalDateTime expiresAt) {
        this.scheduleSeat = scheduleSeat;
        this.reservation = reservation;
        this.expiresAt = expiresAt;
    }

}
