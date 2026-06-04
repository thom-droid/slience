package org.unexpected.slience.batch.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "batch_history")
@Entity
public class BatchHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;
    private String batchType;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String status;
    private Integer successCount;
    private Integer failedCount;
    @Column(name = "error_message", length = Integer.MAX_VALUE)
    private String errorMessage;

}
