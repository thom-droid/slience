package org.unexpected.slience.kobis.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.kobis.api.request.MovieTempUpdateDto;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieTempBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    public int bulkUpdate(Long batchId, List<MovieTempUpdateDto> updates) {

        String sql = """
                UPDATE movie_temps
                    SET watch_grade_nm = ?,
                        adult_yn = ?,
                        restricted_yn = ?
                    WHERE batch_id = ?
                    AND movie_cd = ?
                """;

        int[][] result = jdbcTemplate.batchUpdate(
                sql,
                updates,
                1000,
                (ps, dto) -> {
                    ps.setString(1, dto.watchGradeNm());
                    ps.setBoolean(2, dto.adultYn());
                    ps.setBoolean(3, dto.restrictedYn());
                    ps.setLong(4, batchId);
                    ps.setString(5, dto.movieCd());
                }
        );

        return Arrays.stream(result).flatMapToInt(Arrays::stream).sum();

    }
}
