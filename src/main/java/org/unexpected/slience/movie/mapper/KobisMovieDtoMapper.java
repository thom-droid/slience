package org.unexpected.slience.movie.mapper;

import org.unexpected.slience.movie.domain.entity.DirectorTempEntity;
import org.unexpected.slience.movie.domain.entity.MovieTempEntity;
import org.unexpected.slience.movie.dto.kobis.KobisDirectorDto;
import org.unexpected.slience.movie.dto.kobis.KobisMovieDto;

public class KobisMovieDtoMapper {

    private KobisMovieDtoMapper() { }

    public static MovieTempEntity mapToEntity(KobisMovieDto dto) {
        MovieTempEntity entity = new MovieTempEntity();
        entity.setMovieCd(dto.getMovieCd());
        entity.setMovieNm(dto.getMovieNm());
        entity.setMovieNmEn(dto.getMovieNmEn());
        entity.setOpenDt(dto.getOpenDt());
        entity.setTypeNm(dto.getTypeNm());
        entity.setPrdtStatNm(dto.getPrdtStatNm());
        entity.setRepNationNm(dto.getRepNationNm());
        entity.setRepGenreNm(dto.getRepGenreNm());

        return entity;
    }

    public static DirectorTempEntity mapToEntity(KobisDirectorDto dto) {
        DirectorTempEntity entity = new DirectorTempEntity();
        entity.setName(dto.getPeopleNm());
        return entity;
    }
}
