package org.unexpected.slience.movie.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.unexpected.slience.movie.api.response.MovieDetailFlatDto;
import org.unexpected.slience.movie.api.response.MovieDetailResponse;
import org.unexpected.slience.movie.api.response.MovieScheduleFlatRowDto;
import org.unexpected.slience.movie.api.response.MovieScheduleResponse;
import org.unexpected.slience.movie.application.exception.NoMovieFoundException;
import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.movie.domain.entity.Status;
import org.unexpected.slience.movie.infra.MovieRepository;
import org.unexpected.slience.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieQueryService {

    private final MovieRepository movieRepository;

    public List<MovieEntity> getMovies(Status status, String date, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        if (StringUtils.hasText(date)) {
            LocalDate startDate = DateUtil.parseYYYYMMDDtoLocalDate(date);
            LocalDate endDate = startDate.plusDays(1);
            return movieRepository.findMoviesByStatusAndDate(status, startDate, endDate, pageRequest);
        }
        return movieRepository.findMoviesByStatus(status, pageRequest);
    }

    public List<MovieScheduleResponse> getMoviesByDate(String date) {
        LocalDate startDate = DateUtil.parseYYYYMMDDtoLocalDate(date);
        LocalDateTime startDateTIme = startDate.atStartOfDay();
        LocalDateTime endDateTime = startDate.plusDays(1).atStartOfDay();
        List<MovieScheduleFlatRowDto> flatRows = movieRepository.findMoviesByDate(startDateTIme, endDateTime)
                .stream()
                .toList();

        Map<Long, MovieScheduleResponse> movies = new LinkedHashMap<>();

        for (MovieScheduleFlatRowDto r : flatRows) {
            MovieScheduleResponse row = movies.computeIfAbsent(r.movieId(),
                    id -> new MovieScheduleResponse(id,
                            r.movieCd(),
                            r.movieNm(),
                            new ArrayList<>()
                    )
            );

            MovieScheduleResponse.Screen screen = new MovieScheduleResponse.Screen(
                    r.screenId(),
                    r.screenName(),
                    new ArrayList<>());

            MovieScheduleResponse.Schedule schedule = new MovieScheduleResponse.Schedule(r.seatsLeft(),
                    r.totalSeats(),
                    r.bookedOut(),
                    r.startDateTime(),
                    r.endDateTime());

            screen.schedules().add(schedule);
            row.screens().add(screen);

        }

        return new ArrayList<>(movies.values());
    }

    public MovieDetailResponse getMovieDetail(Long id) {
        List<MovieDetailFlatDto> flat = movieRepository.findMovieDetailById(id);

        MovieDetailFlatDto flatDto = flat.stream()
                .reduce((first, second) -> first)
                .orElseThrow(() -> new NoMovieFoundException(id));

        String directors = flat.stream()
                .map(MovieDetailFlatDto::directorNm)
                .collect(Collectors.joining(","));

        return new MovieDetailResponse(
                flatDto.movieId(),
                flatDto.movieCd(),
                flatDto.movieNm(),
                flatDto.movieNmEn(),
                flatDto.releaseDate(),
                flatDto.showTime(),
                flatDto.status(),
                flatDto.typeNm(),
                flatDto.repNationNm(),
                flatDto.repGenreNm(),
                flatDto.adultYn(),
                directors
        );
    }
}
