package org.unexpected.slience.kobis.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.kobis.domain.entity.DirectorTempEntity;

import java.util.Optional;

@Repository
public interface DirectorTempRepository extends JpaRepository<DirectorTempEntity, Long> {

    Optional<DirectorTempEntity> findByBatchId(Long batchId);
    int deleteAllByBatchId(Long batchId);
}