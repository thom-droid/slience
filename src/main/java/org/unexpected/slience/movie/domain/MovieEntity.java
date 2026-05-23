package org.unexpected.slience.movie.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
