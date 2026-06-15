package org.unexpected.slience.movie.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unexpected.slience.movie.api.response.MovieDetailResponse;
import org.unexpected.slience.movie.api.response.MovieListResponse;
import org.unexpected.slience.movie.api.response.MovieScheduleResponse;
import org.unexpected.slience.movie.application.MovieQueryService;
import org.unexpected.slience.movie.domain.entity.Status;

import java.util.List;

@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
@RestController
public class MovieController {
    private final MovieQueryService movieQueryService;

    @GetMapping
    public List<MovieListResponse> movies(@RequestParam(value = "status", required = false) Status status,
                                          @RequestParam(value = "date", required = false) String date,
                                          @RequestParam(value = "page", defaultValue = "0") int page) {
        return movieQueryService.getMovies(status, date, page)
                .stream()
                .map(MovieListResponse::from)
                .toList();
    }

    @GetMapping("/playing")
    public List<MovieScheduleResponse> getMoviesByDate(@RequestParam(value="movieId", required = false) Long movieId,
                                                       @RequestParam(value = "date", required = true) String date) {
        return movieQueryService.getMovieSchedulesByDate(movieId, date);
    }

    @GetMapping("/{id}")
    public MovieDetailResponse getMovieDetail(@PathVariable Long id) {
        return movieQueryService.getMovieDetail(id);
    }
}
