package org.unexpected.slience.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.user.domain.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "reservations")
@Entity
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    private List<ReservationScheduleSeatEntity> reservationSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
