package org.unexpected.slience.sync.application;

import org.springframework.stereotype.Component;
import org.unexpected.slience.sync.application.exception.SyncServiceNotFound;
import org.unexpected.slience.sync.domain.SyncType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class SyncHistoryFactory {

    private final Map<SyncType, SyncHistoryService> map = new EnumMap<>(SyncType.class);

    public SyncHistoryFactory(List<SyncHistoryService> services) {

        for (SyncHistoryService service : services) {
            map.put(service.syncType(), service);
        }
    }
    public SyncHistoryService getInstance(SyncType type) {
        SyncHistoryService syncHistoryService = map.get(type);
        if (syncHistoryService == null) {
            throw new SyncServiceNotFound(type.name());
        }
        return syncHistoryService;
    }
}
