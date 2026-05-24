package org.unexpected.slience.sync.api.response;

public record SyncHistoryResponse(Long batchId,
                                  String syncType,
                                  String status,
                                  String fetchedCount,
                                  String errorMessage) {
}
