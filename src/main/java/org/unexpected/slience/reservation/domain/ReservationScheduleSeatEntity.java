package org.unexpected.slience.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.schedule.domain.ScheduleSeatEntity;

@Setter
@Getter
@Table(name = "reservation_schedule_seats")
@Entity
public class ReservationScheduleSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_seat_id", nullable = false)
    private ScheduleSeatEntity scheduleSeatEntity;

}
