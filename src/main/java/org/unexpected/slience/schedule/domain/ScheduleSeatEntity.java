package org.unexpected.slience.schedule.domain;

import jakarta.persistence.*;
import org.unexpected.slience.theater.domain.ScreenEntity;

@Table(name = "schedule_seats")
@Entity
public class ScheduleSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private ScreenEntity screen;




}