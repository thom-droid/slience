package org.unexpected.slience.schedule.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {

    private Long id;

    private Long movieId;

    private Long screenId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean bookedOut;
}
