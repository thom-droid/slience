package org.unexpected.slience.screen.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unexpected.slience.schedule.domain.entity.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screens")
public class ScreenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @PositiveOrZero
    private int totalRows;
    @PositiveOrZero
    private int totalCols;
    @PositiveOrZero
    @Column(name = "total_seats")
    private int totalSeats;

    @OneToMany(mappedBy = "screen", orphanRemoval = true)
    private List<SeatEntity> seats = new ArrayList<>();

    @OneToMany(mappedBy = "screen", orphanRemoval = true)
    private List<ScheduleEntity> schedules = new ArrayList<>();
}
