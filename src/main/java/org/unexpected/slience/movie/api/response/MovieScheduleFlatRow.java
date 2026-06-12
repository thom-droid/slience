package org.unexpected.slience.movie.api.response;

import java.time.LocalDateTime;

public record MovieScheduleFlatRow(Long movieId,
                                   String movieCd,
                                   String movieNm,
                                   Long screenId,
                                   String screenName,
                                   Long scheduleId,
                                   int seatsLeft,
                                   int totalSeats,
                                   boolean bookedOut,
                                   LocalDateTime startDateTime,
                                   LocalDateTime endDateTime) {
}
