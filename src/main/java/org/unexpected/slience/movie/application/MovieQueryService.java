package org.unexpected.slience.movie.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.movie.domain.entity.Status;
import org.unexpected.slience.movie.infra.MovieRepository;
import org.unexpected.slience.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

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
    
}
