package org.unexpected.slience.batch.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.batch.application.exception.BatchKeyNotFoundException;
import org.unexpected.slience.batch.domain.BatchHistoryEntity;
import org.unexpected.slience.batch.domain.BatchStatus;
import org.unexpected.slience.batch.domain.BatchType;
import org.unexpected.slience.movie.infra.MovieSyncHistoryRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MovieBatchHistoryService implements BatchHistoryService {

    private final MovieSyncHistoryRepository movieSyncHistoryRepository;

    @Override
    public BatchType syncType() {
        return BatchType.MOVIE;
    }

    @Override
    @Transactional
    public BatchHistoryEntity getOrCreateSynHistory() {
        return movieSyncHistoryRepository.findBySyncTypeAndStatus(syncType().name(), BatchStatus.PROCESSING.name())
                .orElseGet(() ->
                        {
                            BatchHistoryEntity syncHistoryEntity = new BatchHistoryEntity();
                            syncHistoryEntity.setBatchType(syncType().name());
                            syncHistoryEntity.setStatus(BatchStatus.PROCESSING.name());
                            syncHistoryEntity.setStartedAt(LocalDateTime.now());
                            return movieSyncHistoryRepository.save(syncHistoryEntity);
                        }
                );
    }

    @Override
    public BatchHistoryEntity getSynHistory(Long batchId) {
        return movieSyncHistoryRepository.findByBatchId(batchId).orElseThrow(BatchKeyNotFoundException::new);
    }

    @Override
    public void saveSynHistory(BatchHistoryEntity entity) {
        movieSyncHistoryRepository.save(entity);
    }
}
