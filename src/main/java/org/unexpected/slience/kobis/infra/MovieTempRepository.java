package org.unexpected.slience.kobis.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.kobis.domain.entity.MovieTempEntity;

import java.util.Optional;

@Repository
public interface MovieTempRepository extends JpaRepository<MovieTempEntity, Long> {

    Optional<MovieTempEntity> findAllByBatchId(Long batchId);
    int deleteAllByBatchId(Long batchId);

}