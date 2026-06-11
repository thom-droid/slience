package org.unexpected.slience.movie.api.response;

import org.unexpected.slience.movie.domain.entity.Status;

import java.time.LocalDate;

public record MovieDetailResponse(Long movieId,
                                  String movieCd,
                                  String movieNm,
                                  String movieNmEn,
                                  LocalDate releaseDate,
                                  String showTime,
                                  Status status,
                                  String typeNm,
                                  String repNationNm,
                                  String repGenreNm,
                                  boolean adultYn,
                                  String directorNm) {

}
