package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.movie.infra.DirectorRepository;
import org.unexpected.slience.movie.infra.MovieRepository;
import org.unexpected.slience.sync.application.SyncHistoryFactory;
import org.unexpected.slience.sync.application.SyncHistoryService;
import org.unexpected.slience.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.sync.domain.SyncType;
import org.unexpected.slience.kobis.domain.entity.DirectorTempEntity;
import org.unexpected.slience.kobis.domain.entity.MovieTempEntity;
import org.unexpected.slience.kobis.api.response.KobisMovieDto;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.kobis.infra.DirectorTempRepository;
import org.unexpected.slience.kobis.infra.MovieTempRepository;
import org.unexpected.slience.movie.mapper.KobisMovieDtoMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KobisMovieCommandService {

    private final MovieTempRepository movieTempRepository;
    private final DirectorTempRepository directorTempRepository;
    private final MovieRepository movieRepository;
    private final SyncHistoryFactory syncHistoryFactory;
    private final DirectorRepository directorRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SyncHistoryEntity insertKobisMovie(KobisMovieListResponse movieListResponse) {

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
                                directorTempEntity.setName(director.getPeopleNm());
                                return directorTempEntity;
                            })
                            .toList();

                    directorTempEntities.addAll(directorTemp);
                    return movieTempEntity;
                })
                .toList();

        List<MovieTempEntity> inserted = movieTempRepository.saveAll(movieTempEntities);
        directorTempRepository.saveAll(directorTempEntities);

        return syncHistory;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SyncHistoryEntity upsertKobisMovie(SyncHistoryEntity syncHistoryEntity) {
        Long batchId1 = syncHistoryEntity.getBatchId();
        int inserted = movieRepository.mergeMovies(batchId1);
        directorRepository.mergeDirector(batchId1);
        movieRepository.mergeMovieDirectors(batchId1);
        syncHistoryEntity.setFetchedCount(inserted);

        return syncHistoryEntity;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SyncHistoryEntity cleanup(SyncHistoryEntity syncHistoryEntity) {
        movieTempRepository.deleteAllByBatchId(syncHistoryEntity.getBatchId());
        directorTempRepository.deleteAllByBatchId(syncHistoryEntity.getBatchId());

        return syncHistoryEntity;
    }
}
