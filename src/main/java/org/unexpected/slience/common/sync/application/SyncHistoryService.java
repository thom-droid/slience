package org.unexpected.slience.common.sync.application;

import org.unexpected.slience.common.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.common.sync.domain.SyncType;

public interface SyncHistoryService {
    SyncType syncType();
    SyncHistoryEntity getOrCreateSynHistory();
    SyncHistoryEntity getSynHistory(Long batchId);
}
