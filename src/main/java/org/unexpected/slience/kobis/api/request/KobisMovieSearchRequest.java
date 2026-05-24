package org.unexpected.slience.kobis.api.request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Collections;

public record KobisMovieSearchRequest(String movieNm,
                                      String directorNm,
                                      String openStartDt,
                                      String openEndDt,
                                      Integer curPage,
                                      Integer itemPerPage) {
    public static KobisMovieSearchRequest of(String openStartDt, String openEndDt, Integer curPage, Integer itemPerPage) {
        return new KobisMovieSearchRequest(null, null, openStartDt, openEndDt, curPage, itemPerPage);
    }

    public static MultiValueMap<String, String> toMultiValueMap(KobisMovieSearchRequest req) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (req != null) {
            if (StringUtils.hasText(req.movieNm)) {
                map.put(req.movieNm, Collections.singletonList(req.movieNm));
            }
            if (StringUtils.hasText(req.directorNm)) {
                map.put(req.directorNm, Collections.singletonList(req.directorNm));
            }
            map.put("openStartDt", Collections.singletonList(req.openStartDt));
            map.put("openEndDt", Collections.singletonList(req.openEndDt));
            map.put("curPage", Collections.singletonList(String.valueOf(req.curPage)));
            map.put("itemPerPage", Collections.singletonList(String.valueOf(req.itemPerPage)));
        }
        return map;
    }
}
