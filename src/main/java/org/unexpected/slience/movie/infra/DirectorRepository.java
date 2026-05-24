package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.domain.entity.DirectorEntity;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {

    @Modifying
    @Query(value = """
            INSERT INTO directors (name)
            SELECT DISTINCT t.name
            FROM directors_temp t
            WHERE t.batch_id = :batchId
            
            ON CONFLICT (name)
            DO NOTHING;
            """,
            nativeQuery = true)
    int mergeDirector(Long batchId);
}