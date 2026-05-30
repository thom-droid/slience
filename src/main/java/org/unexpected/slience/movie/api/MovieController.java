package org.unexpected.slience.movie.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.unexpected.slience.movie.api.response.MovieListResponse;
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
}
