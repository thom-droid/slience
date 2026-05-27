INSERT INTO seats (screens_id, seat_row, seat_number)
SELECT id, CHR(64 + row_no), seat_no
FROM screens s
CROSS JOIN LATERAL generate_series(1, s.total_rows) AS row_no
CROSS JOIN LATERAL generate_series(1, s.total_cols) AS seat_no;