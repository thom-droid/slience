package org.unexpected.slience.schedule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.screen.domain.SeatEntity;

@Getter
@Setter
@Table(
        name = "schedule_seats",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_schedule_seat_id",
                columnNames = {"schedule_id", "seat_id"}
        )
)
@Entity
public class ScheduleSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

}