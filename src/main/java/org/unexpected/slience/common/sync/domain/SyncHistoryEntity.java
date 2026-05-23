package org.unexpected.slience.common.sync.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "sync_history")
@Entity
public class SyncHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;
    private String syncType;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String status;
    private Integer fetchedCount;
    private Integer failedCount;
    private String errorMessage;

}
