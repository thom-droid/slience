INSERT INTO schedules (movie_id, screen_id, start_time, end_time)
SELECT
    m.id,
    (floor(random() * 9) + 1)::int AS screen_id,
    schedule_time AS start_time,
    schedule_time + INTERVAL '2 hours' AS end_time
FROM (
    SELECT
        id,
        open_dt,
        (
            CASE
                WHEN open_dt > CURRENT_DATE
                    THEN NOW() + INTERVAL '7 days'
                ELSE NOW()
            END
        )
        + ((gs.day_offset * 5 + gs.slot_offset) * INTERVAL '3 hours')
        AS schedule_time
    FROM movies
    CROSS JOIN (
        SELECT d AS day_offset, s AS slot_offset
        FROM generate_series(0, 4) d,   -- 5 days
             generate_series(0, 4) s    -- 5 schedules per day
    ) gs
) m;