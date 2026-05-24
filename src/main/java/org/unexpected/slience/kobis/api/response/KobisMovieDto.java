package org.unexpected.slience.kobis.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KobisMovieDto {

    private String movieCd;

    private String movieNm;

    private String movieNmEn;

    private String openDt;

    private String prdtStatNm;

    private String typeNm;

    private String repNationNm;

    private String repGenreNm;

    private List<KobisDirectorDto> directors;
}