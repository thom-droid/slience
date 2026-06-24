package org.unexpected.slience.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter YYYYMMDDHHMM =  DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    private DateUtil() {}

    public static LocalDate parseYYYYMMDDtoLocalDate(String yyyyMMddStr) {
        return LocalDate.parse(yyyyMMddStr, YYYYMMDD);
    }

    public static LocalDateTime parseYYYYMMDDtoLocalDateTime(String yyyyMMddStr) {
        return parseYYYYMMDDtoLocalDate(yyyyMMddStr).atStartOfDay();
    }

    public static String parseLocalDateToString(LocalDate localDate) {
        return localDate.format(YYYYMMDD);
    }

    public static String parseLocalDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(YYYYMMDDHHMM);
    }
}
