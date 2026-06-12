package org.unexpected.slience.schedule.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.movie.api.response.MovieDetailResponse;
import org.unexpected.slience.movie.api.response.MovieScheduleResponse;
import org.unexpected.slience.schedule.api.response.ScheduleDetailResponse;
import org.unexpected.slience.schedule.api.response.ScheduleFlatRow;
import org.unexpected.slience.schedule.application.exception.NoScheduleFoundException;
import org.unexpected.slience.schedule.infra.ScheduleRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleDetailResponse getScheduleDetail(Long scheduleId) {
        List<ScheduleFlatRow> scheduleDetailFlatRows = scheduleRepository.findScheduleDetail(scheduleId);

        if (scheduleDetailFlatRows.isEmpty()) throw new NoScheduleFoundException(scheduleId);

        Map<Long, ScheduleDetailResponse> schedule = new LinkedHashMap<>();
        for (ScheduleFlatRow r : scheduleDetailFlatRows) {

            ScheduleDetailResponse res = schedule.computeIfAbsent(
                    r.scheduleId(),
                    aLong ->
                            new ScheduleDetailResponse(
                                    new MovieDetailResponse(
                                            r.movieId(),
                                            r.movieCd(),
                                            r.movieNm(),
                                            r.movieNmEn(),
                                            r.releaseDate(),
                                            r.showTime(),
                                            r.status(),
                                            r.typeNm(),
                                            r.repNationNm(),
                                            r.repGenreNm(),
                                            r.adultYn(),
                                            new HashSet<>()
                                    ),
                                    new MovieScheduleResponse.Screen(
                                            r.screenId(),
                                            r.name(),
                                            null
                                    ),
                                    new MovieScheduleResponse.Schedule(
                                            r.scheduleId(),
                                            r.seatsLeft(),
                                            r.totalSeats(),
                                            r.bookedOut(),
                                            r.startDateTime(),
                                            r.endDateTime()
                                    ),
                                    new ArrayList<>()
                            )
            );

            res.movieDetail().directorNms().add(r.directorNm());

            res.seats().add(
                    new ScheduleDetailResponse.Seat(
                            r.seatId(),
                            r.seatRow(),
                            r.seatNumber(),
                            r.booked()
                    )
            );
        }
        return schedule.get(scheduleId);
    }

}
