package org.unexpected.slience.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.reservation.domain.Status;
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

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReservationScheduleSeatEntity> reservationScheduleSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void addReservationScheduleSeat(ReservationScheduleSeatEntity reservationScheduleSeat) {
        if (!this.reservationScheduleSeats.contains(reservationScheduleSeat)
                && reservationScheduleSeat != null) {
            reservationScheduleSeat.setReservation(this);
            reservationScheduleSeats.add(reservationScheduleSeat);
        }
    }

    public void addReservationScheduleSeatList(List<ReservationScheduleSeatEntity> reservationScheduleSeats) {
        for (ReservationScheduleSeatEntity reservationScheduleSeat : reservationScheduleSeats) {
            addReservationScheduleSeat(reservationScheduleSeat);
        }
    }

    public void removeReservationScheduleSeat(ReservationScheduleSeatEntity reservationScheduleSeat) {
        this.reservationScheduleSeats.remove(reservationScheduleSeat);
    }

    public void addUser(UserEntity user) {
        if (user == null) {
            return;
        }

        this.user = user;
        if (!user.getReservations().isEmpty() && !user.getReservations().contains(this)) {
            user.getReservations().add(this);
        }
    }
}
