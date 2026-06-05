package org.unexpected.slience.batch.application;

import org.springframework.stereotype.Component;
import org.unexpected.slience.batch.application.exception.BatchServiceNotFound;
import org.unexpected.slience.batch.domain.BatchType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class BatchHistoryFactory {

    private final Map<BatchType, BatchHistoryService> map = new EnumMap<>(BatchType.class);

    public BatchHistoryFactory(List<BatchHistoryService> services) {

        for (BatchHistoryService service : services) {
            map.put(service.batchType(), service);
        }
    }
    public BatchHistoryService getInstance(BatchType type) {
        BatchHistoryService batchHistoryService = map.get(type);
        if (batchHistoryService == null) {
            throw new BatchServiceNotFound(type.name());
        }
        return batchHistoryService;
    }
}
