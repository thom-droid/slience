package org.unexpected.slience.kobis.application;

import org.unexpected.slience.kobis.api.response.KobisMovieDetailDto;

import java.util.List;
import java.util.Set;

public class KobisAuditChecker {
    private static final Set<String> FILTERING_GENRES = Set.of("성인물", "성인물(에로)", "성인물(애로)", "애로", "에로");

    private static boolean checkAdult(String watchGradeNm) {
        return "청소년관람불가".equals(watchGradeNm);
    }

    private static boolean checkRestricted(String genreNm, String watchGradeNm) {
        return ("청소년관람불가".equals(watchGradeNm) && "멜로/로맨스".equals(genreNm)) || FILTERING_GENRES.contains(genreNm);
    }

    public static AuditChecked doCheck(KobisMovieDetailDto dto) {
        KobisMovieDetailDto.MovieInfo movieInfo = dto.movieInfoResult().movieInfo();
        if (movieInfo == null) return AuditChecked.invalid();

        String movieCd = movieInfo.movieCd();
        String showTm = movieInfo.showTm();
        List<KobisMovieDetailDto.Genres> genres = movieInfo.genres();
        List<KobisMovieDetailDto.Audits> audits = movieInfo.audits();
        if (genres == null || genres.isEmpty() || audits == null || audits.isEmpty())
            return AuditChecked.noInfo(movieCd);
        KobisMovieDetailDto.Audits audit = audits.get(0);
        KobisMovieDetailDto.Genres genre = genres.get(0);
        String genreNm = genre.genreNm();
        String watchGradeNm = audit.watchGradeNm();

        boolean adult = checkAdult(watchGradeNm);
        boolean restricted = checkRestricted(genreNm, watchGradeNm);

        return restricted
                ? AuditChecked.restricted(movieCd, watchGradeNm, showTm, movieInfo.openDt(), movieInfo.prdtStatNm(), adult)
                : AuditChecked.adult(movieCd, watchGradeNm, showTm, movieInfo.openDt(), movieInfo.prdtStatNm(), adult);
    }

    public record AuditChecked(
            String movieCd,
            String watchGradeNm,
            String showTm,
            String openDt,
            String prdtStatNm,
            boolean adultYn,
            boolean restrictedYn,
            boolean valid
    ) {
        public static AuditChecked invalid() {
            return new AuditChecked(null, null, null, null, null, false, false, false);
        }

        public static AuditChecked noInfo(String movieCd) {
            return new AuditChecked(movieCd, "", null, null, null, false, false, true);
        }

        public static AuditChecked adult(String movieCd, String watchGradeNm, String showTm, String openDt, String prdtStatNm, boolean adultYn) {
            return new AuditChecked(movieCd, watchGradeNm, showTm, openDt, prdtStatNm, adultYn, false, true);
        }

        public static AuditChecked restricted(String movieCd, String watchGradeNm, String showTm, String openDt, String prdtStatNm, boolean adultYn) {
            return new AuditChecked(movieCd, watchGradeNm, showTm, openDt, prdtStatNm, adultYn, true, true);
        }
    }
}
