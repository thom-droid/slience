package org.unexpected.slience.sync.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SyncHistory {
    private Long batchId;
    private String syncType;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String status;
    private Integer fetchedCount;
    private Integer failedCount;
    private String errorMessage;

    public void complete() {
        this.status = SyncStatus.COMPLETED.name();
    }

    public void fail(String message) {
        this.status = SyncStatus.FAILED.name();
        this.errorMessage = message;
    }
}
