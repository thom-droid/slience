package org.unexpected.slience.kobis.api.request;

import org.unexpected.slience.kobis.application.KobisAuditChecker;

public record MovieTempUpdateDto(
        String movieCd,
        String watchGradeNm,
        boolean adultYn,
        boolean restrictedYn
) {

    public static MovieTempUpdateDto from(KobisAuditChecker.AuditChecked auditChecked) {
        return new MovieTempUpdateDto(
                auditChecked.movieCd(),
                auditChecked.watchGradeNm(),
                auditChecked.adultYn(),
                auditChecked.restrictedYn()
        );
    }
}
