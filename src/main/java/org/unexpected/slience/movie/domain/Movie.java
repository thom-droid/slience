package org.unexpected.slience.movie.domain;

import lombok.Getter;
import lombok.Setter;
import org.unexpected.slience.movie.domain.entity.Status;
import org.unexpected.slience.schedule.domain.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Movie {

    private Long id;

    private String movieCd;

    private String movieNm;

    private String movieNmEn;

    private LocalDate releaseDate;

    private String prdtStatNm;

    private Status status;

    private String typeNm;

    private String repNationNm;

    private String repGenreNm;

    private boolean adultYn;

    private String showTime;
}
