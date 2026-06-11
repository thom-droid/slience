package org.unexpected.slience.movie.api.response;

import org.unexpected.slience.movie.domain.entity.MovieDirectorEntity;
import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.movie.domain.entity.Status;
import org.unexpected.slience.util.DateUtil;

import java.util.List;

public record MovieListResponse(String movieCd,
                                String movieNm,
                                String movieNmEn,
                                String releaseDate,
                                String prdtStatNm,
                                Status status,
                                String typeNm,
                                String repNationNm,
                                String repGenreNm,
                                List<MovieDirectorResponse> directors) {
    public static MovieListResponse from(MovieEntity entity) {
        List<MovieDirectorResponse> directorResponse = entity.getDirectors()
                .stream()
                .map(MovieDirectorEntity::getDirector)
                .map(MovieDirectorResponse::from)
                .toList();

        return new MovieListResponse(
                entity.getMovieCd(),
                entity.getMovieNm(),
                entity.getMovieNmEn(),
                DateUtil.parseLocalDateToString(entity.getReleaseDate()),
                entity.getPrdtStatNm(),
                entity.getStatus(),
                entity.getTypeNm(),
                entity.getRepNationNm(),
                entity.getRepGenreNm(),
                directorResponse
        );
    }
}