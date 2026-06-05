package org.unexpected.slience.movie.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.unexpected.slience.schedule.domain.ScheduleEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_cd"}),
        indexes = @Index(columnList = "movie_cd", name="movie_idx_movie_cd")
)
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_cd")
    private String movieCd;

    @Column(name = "movie_nm")
    private String movieNm;

    @Column(name = "movie_nm_en")
    private String movieNmEn;

    @Column(name = "open_dt")
    private LocalDate openDt;

    @Column(name = "prdt_stat_nm")
    private String prdtStatNm;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "type_nm")
    private String typeNm;

    @Column(name = "rep_nation_nm")
    private String repNationNm;

    @Column(name = "rep_genre_nm")
    private String repGenreNm;

    @Column(name = "adult_yn")
    private boolean adultYn;

    @Builder.Default
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MovieDirectorEntity> directors = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "movie")
    private List<ScheduleEntity> schedules = new ArrayList<>();

}
