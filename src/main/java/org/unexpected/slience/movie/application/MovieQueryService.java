package org.unexpected.slience.movie.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.unexpected.slience.movie.api.request.MovieListRequest;
import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.movie.infra.MovieRepository;
import org.unexpected.slience.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieQueryService {

    private final MovieRepository movieRepository;

    public List<MovieEntity> getMovies(MovieListRequest request) {
        String date = request.date();
        if (StringUtils.hasText(date)) {
            LocalDate startDate = DateUtil.parseYYYYMMDDtoLocalDate(date);
            LocalDate endDate = startDate.plusDays(1);
            return movieRepository.findMoviesByStatusAndDate(request.status(), startDate, endDate);
        }
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        return movieRepository.findMoviesByStatusAndDate(request.status(), startDate, endDate);

    }

}
