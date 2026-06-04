package org.unexpected.slience.batch.api.response;

public record BatchHistoryResponse(Long batchId,
                                   String batchType,
                                   String status,
                                   String fetchedCount,
                                   String errorMessage) {
}
