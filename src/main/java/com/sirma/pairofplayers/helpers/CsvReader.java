package com.sirma.pairofplayers.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

    private final BufferedReader reader;
    private final String delimiter = ",";

    public CsvReader(String filepath) throws IOException {
        this.reader = new BufferedReader(new FileReader(filepath));
    }

    public List<String[]> readAll() throws IOException {
        List<String[]> records = new ArrayList<>();
        String line;
        boolean isFirstLine = true;
        while((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            String[] record = Arrays.stream(line.split(delimiter))
                    .map(String::trim)
                    .toArray(String[]::new);

            records.add(record);
        }

        return records;
    }

    public void close() throws IOException {
        reader.close();
    }
}
