package org.unexpected.slience.kobis.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KobisMovieListResult {

    private Integer totCnt;
    private List<KobisMovieDto> movieList;
}
