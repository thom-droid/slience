package org.unexpected.slience.sync.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.sync.application.exception.SyncKeyNotFoundException;
import org.unexpected.slience.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.sync.domain.SyncStatus;
import org.unexpected.slience.sync.domain.SyncType;
import org.unexpected.slience.movie.infra.MovieSyncHistoryRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MovieSyncHistoryService implements SyncHistoryService {

    private final MovieSyncHistoryRepository movieSyncHistoryRepository;

    @Override
    public SyncType syncType() {
        return SyncType.MOVIE;
    }

    @Override
    @Transactional
    public SyncHistoryEntity getOrCreateSynHistory() {
        return movieSyncHistoryRepository.findBySyncTypeAndStatus(syncType().name(), SyncStatus.PROCESSING.name())
                .orElseGet(() ->
                        {
                            SyncHistoryEntity syncHistoryEntity = new SyncHistoryEntity();
                            syncHistoryEntity.setSyncType(syncType().name());
                            syncHistoryEntity.setStatus(SyncStatus.PROCESSING.name());
                            syncHistoryEntity.setStartedAt(LocalDateTime.now());
                            return movieSyncHistoryRepository.save(syncHistoryEntity);
                        }
                );
    }

    @Override
    public SyncHistoryEntity getSynHistory(Long batchId) {
        return movieSyncHistoryRepository.findByBatchId(batchId).orElseThrow(SyncKeyNotFoundException::new);
    }

    @Override
    public void saveSynHistory(SyncHistoryEntity entity) {
        movieSyncHistoryRepository.save(entity);
    }
}
