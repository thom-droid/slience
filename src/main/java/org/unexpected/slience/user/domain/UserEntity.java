package org.unexpected.slience.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.reservation.domain.ReservationEntity;

import java.util.List;

@Setter
@Getter
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_email", columnNames = "email")
        }
)
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReservationEntity> reservations;
}
