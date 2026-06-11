package org.unexpected.slience.movie.domain;

import org.unexpected.slience.movie.domain.entity.MovieDirectorEntity;
import org.unexpected.slience.movie.domain.entity.MovieEntity;

public class MovieMapper {

    private MovieMapper() {
    }

    public static Movie toMovie(MovieEntity e) {
        Movie movie = new Movie();
        movie.setId(e.getId());
        movie.setMovieCd(e.getMovieCd());
        movie.setMovieNm(e.getMovieNm());
        movie.setMovieNmEn(e.getMovieNmEn());
        movie.setReleaseDate(e.getReleaseDate());
        movie.setTypeNm(e.getTypeNm());
        movie.setPrdtStatNm(e.getPrdtStatNm());
        movie.setRepNationNm(e.getRepNationNm());
        movie.setAdultYn(e.isAdultYn());
        movie.setShowTime(e.getShowTime());

        return movie;
    }

    public static Director toDirector(MovieDirectorEntity e) {
        Director director = new Director();
        director.setId(e.getDirector().getId());
        director.setName(e.getDirector().getName());
        return director;
    }
}
