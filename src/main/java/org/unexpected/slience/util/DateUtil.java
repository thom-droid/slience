package org.unexpected.slience.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {}

    public static LocalDate parseYYYYMMDDtoLocalDate(String yyyyMMddStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(yyyyMMddStr, formatter);
    }
}
