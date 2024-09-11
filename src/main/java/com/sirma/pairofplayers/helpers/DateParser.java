package com.sirma.pairofplayers.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static LocalDate parseDateWithMultipleFormats(String dateStr) {
        for (DatePattern pattern : DatePattern.values()) {
            try {
                return LocalDate.parse(dateStr, pattern.getFormatter());
            } catch (DateTimeParseException e) {
            }
        }

        throw new IllegalArgumentException("Invalid date format: " + dateStr);
    }
}
