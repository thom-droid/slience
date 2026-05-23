package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.domain.entity.DirectorTempEntity;

import java.util.Optional;

@Repository
public interface DirectorTempEntityRepository extends JpaRepository<DirectorTempEntity, Long> {

    Optional<DirectorTempEntity> findByBatchId(Long batchId);
}