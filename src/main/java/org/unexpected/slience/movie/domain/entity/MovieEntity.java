package org.unexpected.slience.movie.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.unexpected.slience.schedule.domain.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_cd"})
)
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieCd;

    private String movieNm;

    private String movieNmEn;

    private String openDt;

    private String prdtStatNm;

    private String typeNm;

    private String repNationNm;

    private String repGenreNm;

    @Builder.Default
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<MovieDirectorEntity> directors = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "movie")
    private List<ScheduleEntity> schedules = new ArrayList<>();

}
