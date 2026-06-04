package org.unexpected.slience.kobis.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "movie_temps")
@Entity
public class MovieTempEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long batchId;

    private String movieCd;

    private String movieNm;

    private String movieNmEn;

    private String openDt;

    private String prdtStatNm;

    private String typeNm;

    private String repNationNm;

    private String repGenreNm;
    private String watchGradeNm;
    private boolean adultYn;
    private boolean restrictedYn;
}
