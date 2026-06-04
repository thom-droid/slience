package org.unexpected.slience.kobis.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.kobis.domain.entity.MovieTempEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieTempRepository extends JpaRepository<MovieTempEntity, Long> {

    Optional<MovieTempEntity> findAllByBatchId(Long batchId);

    @Modifying
    @Query("""
                delete from MovieTempEntity m
                where m.batchId = :batchId
            """)
    int deleteByBatchId(Long batchId);

    @Query(value = """
                SELECT movie_cd
                FROM movie_temps mt
                WHERE mt.batch_id = :batchId
                AND NOT EXISTS(SELECT 1 FROM movies m WHERE m.movie_cd = mt.movie_cd)
            """, nativeQuery = true)
    List<String> findNewMoviesByBatchId(Long batchId);
}