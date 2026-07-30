package org.unexpected.slience.kobis.api.request;

import org.springframework.util.StringUtils;
import org.unexpected.slience.kobis.application.KobisAuditChecker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record KobisMovieDetailUpdateDto(
        String movieCd,
        String watchGradeNm,
        String showTime,
        LocalDate releaseDate,
        String prdtStatNm,
        boolean adultYn,
        boolean restrictedYn
) {

    public static KobisMovieDetailUpdateDto from(KobisAuditChecker.AuditChecked auditChecked) {
        String d = auditChecked.openDt();
        LocalDate releaseDate = !StringUtils.hasText(d) ? null : LocalDate.parse(d, DateTimeFormatter.BASIC_ISO_DATE);
        return new KobisMovieDetailUpdateDto(
                auditChecked.movieCd(),
                auditChecked.watchGradeNm(),
                auditChecked.showTm(),
                releaseDate,
                auditChecked.prdtStatNm(),
                auditChecked.adultYn(),
                auditChecked.restrictedYn()
        );
    }
}
