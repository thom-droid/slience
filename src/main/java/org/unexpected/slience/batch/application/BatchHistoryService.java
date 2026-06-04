package org.unexpected.slience.batch.application;

import org.unexpected.slience.batch.domain.BatchHistoryEntity;
import org.unexpected.slience.batch.domain.BatchType;

public interface BatchHistoryService {
    BatchType syncType();
    BatchHistoryEntity getOrCreateSynHistory();
    BatchHistoryEntity getSynHistory(Long batchId);
//    void success(SyncHistory syncHistory);
//    void failure(SyncHistory syncHistory);
    void saveSynHistory(BatchHistoryEntity entity);
}
