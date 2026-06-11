package org.unexpected.slience.movie.api.response;

import java.time.LocalDateTime;
import java.util.List;

public record MovieScheduleResponse(
        Long movieId,
        String movieCd,
        String movieNm,
        List<Screen> screens
) {

    public record Screen (
            Long screenId,
            String name,
            List<Schedule> schedules
    ) {}

    public record Schedule (
            int seatsLeft,
            int totalSeats,
            boolean bookedOut,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {}
}
