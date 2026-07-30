package org.unexpected.slience.movie.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.kobis.api.request.KobisMovieDetailUpdateDto;
import org.unexpected.slience.kobis.infra.MovieBulkRepository;
import org.unexpected.slience.movie.infra.MovieRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieCommandService {

    private final MovieRepository movieRepository;
    private final MovieBulkRepository movieBulkRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int bulkUpdateMovieDetail(List<KobisMovieDetailUpdateDto> updateDtos) {
        return movieBulkRepository.bulkUpdate(updateDtos);
    }
}
