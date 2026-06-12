package org.unexpected.slience.batch.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.batch.application.exception.BatchKeyNotFoundException;
import org.unexpected.slience.batch.domain.BatchHistory;
import org.unexpected.slience.batch.domain.BatchHistoryEntity;
import org.unexpected.slience.batch.domain.BatchHistoryMapper;
import org.unexpected.slience.batch.domain.BatchStatus;
import org.unexpected.slience.movie.infra.BatchHistoryRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BatchJpaStore implements BatchStore {

    private final BatchHistoryRepository batchHistoryRepository;

    @Override
    public BatchHistory getBatch(Long batchId) {
        return batchHistoryRepository.findByBatchId(batchId)
                .map(BatchHistoryMapper::toDomain)
                .orElseThrow(() -> new BatchKeyNotFoundException(batchId));
    }

    @Override
    public Optional<BatchHistory> findProcessing(String batchType) {
        return batchHistoryRepository
                .findByBatchTypeAndStatus(batchType, BatchStatus.PROCESSING.name())
                .map(BatchHistoryMapper::toDomain);
    }

    @Override
    public BatchHistory save(BatchHistory batchHistory) {
        BatchHistoryEntity entity = batchHistory.getBatchId() == null
                ? new BatchHistoryEntity()
                : batchHistoryRepository.findById(batchHistory.getBatchId())
                    .orElseThrow(() -> new BatchKeyNotFoundException(batchHistory.getBatchId()));

        BatchHistoryMapper.updateEntity(batchHistory, entity);
        return BatchHistoryMapper.toDomain(batchHistoryRepository.save(entity));
    }
}
