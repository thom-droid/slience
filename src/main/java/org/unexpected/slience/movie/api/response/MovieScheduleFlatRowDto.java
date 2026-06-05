package org.unexpected.slience.movie.api.response;

import java.time.LocalDateTime;

public record MovieScheduleFlatRowDto(Long movieId,
                                      String movieCd,
                                      String movieNm,
                                      Long screenId,
                                      String screenName,
                                      int seatsLeft,
                                      int totalSeats,
                                      boolean bookedOut,
                                      LocalDateTime startDateTime,
                                      LocalDateTime endDateTime) {
}
