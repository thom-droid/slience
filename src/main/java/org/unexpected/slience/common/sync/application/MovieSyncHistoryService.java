package org.unexpected.slience.common.sync.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.common.sync.SyncKeyNotFoundException;
import org.unexpected.slience.common.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.common.sync.domain.SyncStatus;
import org.unexpected.slience.common.sync.domain.SyncType;
import org.unexpected.slience.movie.infra.MovieSyncHistoryEntityRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MovieSyncHistoryService implements SyncHistoryService {

    private final MovieSyncHistoryEntityRepository movieSyncHistoryRepository;

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
}
