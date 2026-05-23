package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.domain.entity.MovieTempEntity;

import java.util.Optional;

@Repository
public interface MovieTempEntityRepository extends JpaRepository<MovieTempEntity, Long> {

    Optional<MovieTempEntity> findAllByBatchId(Long batchId);

}