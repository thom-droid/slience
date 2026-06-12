package org.unexpected.slience.batch.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.batch.domain.BatchHistory;

@RequiredArgsConstructor
@Service
public class BatchCommandService {

    private final BatchStore batchStore;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public BatchHistory save(BatchHistory batchHistory) {
        return batchStore.save(batchHistory);
    }
}
