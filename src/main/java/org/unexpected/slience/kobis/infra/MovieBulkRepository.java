package org.unexpected.slience.kobis.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.kobis.api.request.KobisMovieDetailUpdateDto;

import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public int bulkUpdate(List<KobisMovieDetailUpdateDto> updates) {

        String sql = """
                 UPDATE movies m
                    SET show_time = ?,
                        release_date = ?,
                        prdt_stat_nm = ?,
                        status = CASE WHEN ? = '개봉' THEN 'PLAYING'
                                      WHEN ? = '개봉예정' THEN 'COMING_SOON'
                                      ELSE 'CLOSED' END,
                        adult_yn = ?,
                        restricted = ?,
                        modified_date = NOW(),
                        synced = (
                                    CAST(? AS varchar) IS NOT NULL
                                    AND CAST(? AS date) IS NOT NULL
                                    AND COALESCE(CAST(? AS varchar), '') <> ''
                                 )
                     WHERE movie_cd = ?;
                """;

        int[][] result = jdbcTemplate.batchUpdate(
                sql,
                updates,
                1000,
                (ps, dto) -> {
                    LocalDate releaseDate = dto.releaseDate();
                    Date sqlDate = releaseDate == null
                            ? null
                            : Date.valueOf(releaseDate);

                    ps.setString(1, dto.showTime());

                    if (sqlDate == null) {
                        ps.setNull(2, Types.DATE);
                    } else {
                        ps.setDate(2, sqlDate);
                    }

                    ps.setString(3, dto.prdtStatNm());
                    ps.setString(4, dto.prdtStatNm());
                    ps.setString(5, dto.prdtStatNm());

                    ps.setBoolean(6, dto.adultYn());
                    ps.setBoolean(7, dto.restrictedYn());

                    ps.setString(8, dto.showTime());

                    if (sqlDate == null) {
                        ps.setNull(9, Types.DATE);
                    } else {
                        ps.setDate(9, sqlDate);
                    }

                    ps.setString(10, dto.prdtStatNm());
                    ps.setString(11, dto.movieCd());
                }
        );

        return Arrays.stream(result).flatMapToInt(Arrays::stream).sum();
    }

}
