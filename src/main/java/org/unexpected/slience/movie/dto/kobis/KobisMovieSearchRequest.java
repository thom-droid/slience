package org.unexpected.slience.movie.dto.kobis;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KobisMovieSearchRequest {

    private String movieNm;
    private String directorNm;
    private String openStartDt;
    private String openEndDt;

    @Builder.Default
    private Integer curPage = 1;

    @Builder.Default
    private Integer itemPerPage = 10;
}