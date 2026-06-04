package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.batch.domain.BatchHistoryEntity;

import java.util.Optional;

@Repository
public interface MovieSyncHistoryRepository extends JpaRepository<BatchHistoryEntity, Long> {

    Optional<BatchHistoryEntity> findBySyncTypeAndStatus(String syncType, String status);

    Optional<BatchHistoryEntity> findByBatchId(Long batchId);
}