package com.telstra.codechallenge.starrepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class RepositoryUtil {
    public static String getYYYYMMDD(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static LocalDate getLastLocalDate(int days) {
        LocalDate now = LocalDate.now();
        return now.minusDays(days + now.getDayOfWeek().getValue() - 1);
    }
}
