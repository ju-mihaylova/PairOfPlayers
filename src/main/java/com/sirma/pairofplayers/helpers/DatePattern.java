package com.sirma.pairofplayers.helpers;

import java.time.format.DateTimeFormatter;

public enum DatePattern {
    M_D_YYYY("M/d/yyyy"),
    YYYY_MM_DD("yyyy-MM-dd"),
    D_M_YYYY("d/M/yyyy"),
    MM_DD_YYYY("MM/dd/yyyy"),
    DD_MM_YYYY("dd-MM-yyyy");

    private final DateTimeFormatter formatter;

    DatePattern(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
