package org.unexpected.slience.batch.domain;

public class BatchHistoryMapper {

    public static BatchHistory toDomain(BatchHistoryEntity e) {
        BatchHistory b = new BatchHistory();
        b.setBatchId(e.getBatchId());
        b.setBatchType(e.getBatchType());
        b.setStatus(e.getStatus());
        b.setStartedAt(e.getStartedAt());
        b.setFinishedAt(e.getFinishedAt());
        b.setFailedCount(e.getFailedCount());
        b.setErrorMessage(e.getErrorMessage());
        b.setSuccessCount(e.getSuccessCount());
        return b;
    }

    public static void updateEntity(BatchHistory b, BatchHistoryEntity e) {
        e.setBatchType(b.getBatchType());
        e.setStatus(b.getStatus());
        e.setStartedAt(b.getStartedAt());
        e.setFinishedAt(b.getFinishedAt());
        e.setFailedCount(b.getFailedCount());
        e.setErrorMessage(b.getErrorMessage());
        e.setSuccessCount(b.getSuccessCount());
    }
}
