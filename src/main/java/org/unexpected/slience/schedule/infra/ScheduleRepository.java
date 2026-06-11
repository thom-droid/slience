package org.unexpected.slience.schedule.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.schedule.domain.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Modifying
    @Query(value = """
            INSERT INTO schedules (movie_id, screen_id, start_time, end_time, seats_left)
            SELECT
                m.movie_id,
                m.screen_id as screen_id,
                schedule_time AS start_time,
                schedule_time + (m.show_time::int * INTERVAL '1 minute') AS end_time,
                m.total_seats as seats_left
            FROM (
                SELECT
                    m2.id as movie_id,
                    release_date,
                    (
                        CASE
                            WHEN release_date > CURRENT_DATE
                                THEN NOW() + INTERVAL '7 days'
                            ELSE NOW()
                        END
                    )
                    + ((gs.day_offset * 5 + gs.slot_offset) * INTERVAL '3 hours')
                    AS schedule_time,
                    sc.id as screen_id,
                    sc.total_seats,
                    m2.show_time
                FROM (SELECT m1.id, m1.release_date, m1.show_time
                        FROM movies m1
                        WHERE m1.status = 'PLAYING'
                        AND NOT EXISTS (SELECT 1 FROM schedules s WHERE s.movie_id = m1.id)) m2
                CROSS JOIN (
                    SELECT d AS day_offset, s AS slot_offset
                    FROM GENERATE_SERIES(0, 4) d,   -- 5 days
                         GENERATE_SERIES(0, 4) s    -- 5 schedules per day
                ) gs
                CROSS JOIN LATERAL (
                    SELECT id, total_seats
                    FROM screens
                    ORDER BY RANDOM()
                    LIMIT 1
                ) sc
            ) m
            """, nativeQuery = true)
    int syncSchedules();
}