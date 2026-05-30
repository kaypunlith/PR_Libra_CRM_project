package com.ut.nlSystemAPi.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDate {

    public static String FormatMonthYear(String dateBeforeFormat, String pattern) {
        DateTimeFormatter myFormatMonthYear = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateAfterFormat = LocalDateTime.parse(dateBeforeFormat + "T10:15:30");
        return dateAfterFormat.format(myFormatMonthYear);
    }
}
