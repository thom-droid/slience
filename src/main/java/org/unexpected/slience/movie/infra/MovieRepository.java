package org.unexpected.slience.movie.infra;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.api.response.MovieScheduleFlatRowDto;
import org.unexpected.slience.movie.api.response.MovieScheduleRowDto;
import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.movie.domain.entity.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Modifying
    @Query(value = """
                INSERT INTO movies (movie_cd, movie_nm, movie_nm_en, open_dt,
                                    prdt_stat_nm, status, rep_genre_nm, rep_nation_nm, type_nm, adult_yn)
                SELECT t.movie_cd, t.movie_nm, t.movie_nm_en, to_date(t.open_dt, 'YYYYMMDD'),
                       t.prdt_stat_nm, CASE WHEN to_date(t.open_dt, 'YYYYMMDD') + INTERVAL '30 DAYS' < CURRENT_DATE
                                            THEN 'CLOSED'
                                            ELSE s.status
                                       END AS status,
                       t.rep_genre_nm, t.rep_nation_nm, t.type_nm, t.adult_yn
                FROM movie_temps t
                LEFT JOIN (SELECT 'PLAYING' AS status, '개봉' AS prdt_stat_nm
                                UNION ALL
                            SELECT 'COMING_SOON', '개봉예정'
                                UNION ALL
                            SELECT 'CLOSED', '상영종료') s ON t.prdt_stat_nm = s.PRDT_STAT_NM
                WHERE t.batch_id = :batchId
                AND   t.restricted_yn = FALSE
            
                ON CONFLICT (movie_cd)
                DO UPDATE set
                    movie_nm = EXCLUDED.movie_nm,
                    open_dt = EXCLUDED.open_dt,
                    prdt_stat_nm = EXCLUDED.prdt_stat_nm,
                    status = EXCLUDED.status
            """,
            nativeQuery = true)
    int mergeMovies(Long batchId);

    @Modifying
    @Query(value = """
                INSERT INTO movie_directors (director_id, movie_id)
            
                SELECT d.id AS director_id,
                       m.id AS movie_id
                FROM directors_temp t
            
                JOIN movies m ON m.movie_cd = t.movie_cd
                JOIN directors d ON d.name = t.name
                WHERE t.batch_id = :batchId
            
                ON CONFLICT (movie_id, director_id)
                DO NOTHING;
            """,
            nativeQuery = true)
    int mergeMovieDirectors(@Param(value = "batchId") Long batchId);

    @Query(value = """
            select m
            from MovieEntity m
            where (:status is null or m.status = :status)
            and m.openDt >= :startDate
            and m.openDt < :endDate
            """)
    List<MovieEntity> findMoviesByStatusAndDate(@Param("status") Status status,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate,
                                                Pageable pageRequest);

    @Query(value = """
            select m
            from MovieEntity m
            where (:status is null or m.status = :status)
            """)
    List<MovieEntity> findMoviesByStatus(@Param("status") Status status,
                                         Pageable pageRequest);

    @Query(value = """
            select new org.unexpected.slience.movie.api.response.MovieScheduleFlatRowDto(
                        m.id,
                        m.movieCd,
                        m.movieNm,
                        s.id,
                        s.name,
                        sch.seatsLeft,
                        s.totalSeats,
                        sch.bookedOut,
                        sch.startTime,
                        sch.endTime
            )
            from MovieEntity m
            join m.schedules sch
            join sch.screen s
            where m.status = 'PLAYING'
              and sch.startTime >= :startDateTime
              and sch.startTime <  :endDateTime
            """)
    List<MovieScheduleFlatRowDto> findMoviesByDate(@Param("startTime") LocalDateTime startDateTime,
                                                          @Param("endTime") LocalDateTime endDateTime);
}
