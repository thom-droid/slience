package org.unexpected.slience.movie.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.domain.entity.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Modifying
    @Query(value = """
                INSERT INTO movies (movie_cd, movie_nm, movie_nm_en, open_dt,
                                    prdt_stat_nm, rep_genre_nm, rep_nation_nm, type_nm)
                SELECT t.movie_cd, t.movie_nm, t.movie_nm_en, TO_DATE(t.open_dt, 'YYYYMMDD'),
                                   t.prdt_stat_nm, t.rep_genre_nm, t.rep_nation_nm, t.type_nm
                FROM movie_temps t
                WHERE t.batch_id = :batchId
            
                ON CONFLICT (movie_cd)
                DO UPDATE SET
                    movie_nm = EXCLUDED.movie_nm,
                    open_dt = EXCLUDED.open_dt
            """,
            nativeQuery = true)
    int mergeMovies(Long batchId);

    @Modifying
    @Query(value = """
                INSERT INTO movie_directors (director_id, movie_id)
            
                SELECT d.id as director_id,
                       m.id as movie_id
                FROM directors_temp t
            
                JOIN movies m ON m.movie_cd = t.movie_cd
                JOIN directors d ON d.name = t.name
                WHERE t.batch_id = :batchId
            
                ON CONFLICT (movie_id, director_id)
                DO NOTHING;
            """,
            nativeQuery = true)
    int mergeMovieDirectors(@Param(value = "batchId") Long batchId);

}