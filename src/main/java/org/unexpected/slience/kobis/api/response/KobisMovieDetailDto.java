package org.unexpected.slience.kobis.api.response;

import java.util.List;

public record KobisMovieDetailDto(MovieInfoResult movieInfoResult) {
    public record MovieInfoResult(MovieInfo movieInfo) {}

    public record MovieInfo(String movieCd,
                            String movieNm,
                            String showTm,
                            String openDt,
                            String prdtStatNm,
                            List<Audits> audits,
                            List<Genres> genres
    ) {}

    public record Audits(String auditNo, String watchGradeNm) {}
    public record Genres(String genreNm) {}
}
