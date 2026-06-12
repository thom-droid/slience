package org.unexpected.slience.schedule.api.response;

import org.unexpected.slience.movie.api.response.MovieDetailResponse;
import org.unexpected.slience.movie.api.response.MovieScheduleResponse;

import java.util.List;

public record ScheduleDetailResponse(MovieDetailResponse movieDetail,
                                     MovieScheduleResponse.Screen screen,
                                     MovieScheduleResponse.Schedule schedule,
                                     List<Seat> seats) {
    public record Seat(Long id,
                       String seatRow,
                       String seatNumber,
                       boolean booked) {}
}
