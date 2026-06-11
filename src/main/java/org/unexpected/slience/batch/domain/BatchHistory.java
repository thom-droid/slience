package org.unexpected.slience.batch.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BatchHistory {
    private Long batchId;
    private String batchType;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String status;
    private Integer successCount;
    private Integer failedCount;
    private String errorMessage;

    public void complete() {
        this.status = BatchStatus.COMPLETED.name();
        this.setFinishedAt(LocalDateTime.now());
    }

    public void completeWithNoUpdate() {
        this.status = BatchStatus.COMPLETED.name();
        this.setSuccessCount(0);
        this.setFailedCount(0);
        this.setFinishedAt(LocalDateTime.now());
    }

    public void fail(String message) {
        this.status = BatchStatus.FAILED.name();
        this.errorMessage = message;
    }
}
