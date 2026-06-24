package org.unexpected.slience.reservation.api.response;

import org.unexpected.slience.reservation.domain.Status;

public record CreateReservationResponse(Long movieId,
                                        String movieTitle,
                                        String movieTitleEn,
                                        Long screenId,
                                        String screenName,
                                        Long scheduleId,
                                        String startDateTime,
                                        String endDateTime,
                                        String seatInfo,
                                        Long reservationId,
                                        String createdAt,
                                        Status status,
                                        String expiresAt) {

}
