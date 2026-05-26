package org.unexpected.slience.kobis.application;

import org.springframework.util.StringUtils;

import java.util.Set;

public class KobisAdultGenreFilter {
    private static final Set<String> FILTERING_MOVIES = Set.of("성인물", "성인물(에로)", "성인물(애로)", "애로", "에로");
    public static boolean shouldFilter(String genreName) {
        if (StringUtils.hasText(genreName)) {
            return FILTERING_MOVIES.contains(genreName);
        }
        return true;
    }

}
