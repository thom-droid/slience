package org.unexpected.slience.batch.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.batch.domain.BatchHistory;

@RequiredArgsConstructor
@Service
public class BatchHistoryQueryService {

    private final BatchStore batchStore;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BatchHistory getOrCreate(String batchType) {
        return batchStore.findProcessing(batchType)
                .orElseGet(() -> batchStore.save(BatchHistory.start(batchType)));
    }

    public BatchHistory getBatchHistory(Long batchId) {
        return batchStore.getBatch(batchId);
    }

}
