package org.unexpected.slience.movie.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.common.sync.application.SyncHistoryFactory;
import org.unexpected.slience.common.sync.application.SyncHistoryService;
import org.unexpected.slience.common.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.common.sync.domain.SyncType;
import org.unexpected.slience.movie.domain.entity.DirectorTempEntity;
import org.unexpected.slience.movie.domain.entity.MovieTempEntity;
import org.unexpected.slience.movie.dto.kobis.KobisMovieDto;
import org.unexpected.slience.movie.dto.kobis.KobisMovieListResponse;
import org.unexpected.slience.movie.infra.DirectorTempEntityRepository;
import org.unexpected.slience.movie.infra.MovieTempEntityRepository;
import org.unexpected.slience.movie.mapper.KobisMovieDtoMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KobisMovieService {

    private final MovieTempEntityRepository movieTempRepository;
    private final DirectorTempEntityRepository directorTempEntityRepository;
    private final SyncHistoryFactory syncHistoryFactory;

    @Transactional
    public Long insertKobisMovie(KobisMovieListResponse movieListResponse) {

        SyncHistoryService syncHistoryService = syncHistoryFactory.getInstance(SyncType.MOVIE);
        SyncHistoryEntity syncHistory = syncHistoryService.getOrCreateSynHistory();
        Long batchId = syncHistory.getBatchId();

        List<KobisMovieDto> movieList = movieListResponse.getMovieListResult().getMovieList();

        List<DirectorTempEntity> directorTempEntities = new ArrayList<>();
        List<MovieTempEntity> movieTempEntities = movieList
                .stream()
                .map(dto -> {
                    MovieTempEntity movieTempEntity = KobisMovieDtoMapper.mapToEntity(dto);
                    movieTempEntity.setBatchId(batchId);

                    List<DirectorTempEntity> directorTemp = dto.getDirectors().stream()
                            .map(director -> {
                                DirectorTempEntity directorTempEntity = KobisMovieDtoMapper.mapToEntity(director);
                                directorTempEntity.setBatchId(batchId);
                                directorTempEntity.setMovieCd(movieTempEntity.getMovieCd());
                                return directorTempEntity;
                            })
                            .toList();

                    directorTempEntities.addAll(directorTemp);
                    return movieTempEntity;
                })
                .toList();

        List<MovieTempEntity> inserted = movieTempRepository.saveAll(movieTempEntities);
        directorTempEntityRepository.saveAll(directorTempEntities);

        return batchId;
    }

    @Transactional
    public void upsertKobisMovie(Long batchId) {
        SyncHistoryService syncHistoryService = syncHistoryFactory.getInstance(SyncType.MOVIE);
        SyncHistoryEntity syncHistory = syncHistoryService.getSynHistory(batchId);



    }
}
