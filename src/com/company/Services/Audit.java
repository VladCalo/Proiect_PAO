package com.company.Services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {
    FileWriter writer;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logAction(String action) throws IOException {
        writer.append(action);
        writer.append(",");
        writer.append(formatter.format(LocalDateTime.now()));
        writer.append("\n");
        writer.flush();
    }

    public Audit() throws IOException {
        this.writer = new FileWriter("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/audit.csv");
    }
}
