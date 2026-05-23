package org.unexpected.slience.movie.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Table(name = "directors")
@Entity
public class DirectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<MovieDirectorEntity> movies;

}
