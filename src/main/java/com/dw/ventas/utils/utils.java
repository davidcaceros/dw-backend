package com.dw.ventas.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class utils {

    public static LocalDateTime stringToLocalDateTime(final String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            System.err.println("Error parsing LocalDateTime: " + e.getMessage());
            return null;
        }
    }

    public static String localDateTimeToString(final LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static String formattedDateTime(final LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public static LocalDateTime frontToBackendLocalDateTime(final String fechaString) {
        LocalDate localDate = LocalDate.parse(fechaString);
        return localDate.atStartOfDay();
    }
}
