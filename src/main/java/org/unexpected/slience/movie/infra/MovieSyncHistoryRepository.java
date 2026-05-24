package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.sync.domain.SyncHistoryEntity;

import java.util.Optional;

@Repository
public interface MovieSyncHistoryRepository extends JpaRepository<SyncHistoryEntity, Long> {

    Optional<SyncHistoryEntity> findBySyncTypeAndStatus(String syncType, String status);

    Optional<SyncHistoryEntity> findByBatchId(Long batchId);
}