package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.domain.entity.MovieEntity;

@Repository
public interface MovieEntityRepository extends JpaRepository<MovieEntity, Long> {

//    @Modifying
//    @NativeQuery(value = """
//    """)
//    void mergeMovies(Long batchId);

}