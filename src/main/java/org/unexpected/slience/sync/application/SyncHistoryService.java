package org.unexpected.slience.sync.application;

import org.unexpected.slience.sync.domain.SyncHistory;
import org.unexpected.slience.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.sync.domain.SyncType;

public interface SyncHistoryService {
    SyncType syncType();
    SyncHistoryEntity getOrCreateSynHistory();
    SyncHistoryEntity getSynHistory(Long batchId);
//    void success(SyncHistory syncHistory);
//    void failure(SyncHistory syncHistory);
    void saveSynHistory(SyncHistoryEntity entity);
}
