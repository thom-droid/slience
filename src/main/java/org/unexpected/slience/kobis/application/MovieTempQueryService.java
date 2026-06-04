package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.kobis.infra.MovieTempRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieTempQueryService {

    private final MovieTempRepository movieTempRepository;

    @Transactional
    public List<String> findNewMoviesByBatchId(Long batchId) {
        return movieTempRepository.findNewMoviesByBatchId(batchId);
    }
}
