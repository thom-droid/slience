package org.unexpected.slience.theater.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unexpected.slience.schedule.domain.ScheduleEntity;

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

    private int totalRows;
    private int totalCols;

    @OneToMany(mappedBy = "screen", orphanRemoval = true)
    private List<SeatEntity> seats = new ArrayList<>();

    @OneToMany(mappedBy = "screen", orphanRemoval = true)
    private List<ScheduleEntity> schedules = new ArrayList<>();
}
