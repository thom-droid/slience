package org.unexpected.slience.schedule.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unexpected.slience.movie.domain.MovieEntity;
import org.unexpected.slience.theater.domain.ScreenEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "movies_id")
    private MovieEntity movie;

    @OneToOne(mappedBy = "screens_id")
    private ScreenEntity screen;

    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

}
