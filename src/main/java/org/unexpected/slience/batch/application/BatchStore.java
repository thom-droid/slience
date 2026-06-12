package org.unexpected.slience.batch.application;

import org.unexpected.slience.batch.domain.BatchHistory;

import java.util.Optional;

public interface BatchStore {
    BatchHistory getBatch(Long batchId);
    Optional<BatchHistory> findProcessing(String batchType);
    BatchHistory save(BatchHistory batchHistory);
}
