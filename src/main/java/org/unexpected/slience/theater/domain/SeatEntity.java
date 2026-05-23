package org.unexpected.slience.theater.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seats",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_seat",
                columnNames = {"screens_id", "row", "seat_number"}
        )
    }
)
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screens_id")
    private ScreenEntity screen;

    @Column(nullable = false)
    private String row;

    @Column(nullable = false)
    private String seatNumber;

}
