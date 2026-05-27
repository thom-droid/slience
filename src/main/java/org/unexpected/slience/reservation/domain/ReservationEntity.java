package org.unexpected.slience.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.schedule.domain.ScheduleEntity;
import org.unexpected.slience.user.domain.UserEntity;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "reservations")
@Entity
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Enumerated(value = EnumType.STRING)
    private Status status;

}
