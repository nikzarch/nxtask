package com.example.nxtask.util;

import com.example.nxtask.model.CDRRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Класс-компонент, работающий с файлами
 */
@Component
public class FileManager {
    /**
     * название директории для отчетов
     */
    private static final String REPORTS_DIR = "reports";

    /**
     * Сохраняет переданные CDR записи в директорию reports в файл с именем msisdn + "_" + UUID + ".csv"
     * @param cdrs
     * @param UUID
     * @param msisdn
     * @throws IOException
     */
    public void saveCDRToCSV(List<CDRRecord> cdrs, String UUID, String msisdn) throws IOException {
        File reportsDir = new File(REPORTS_DIR);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        File reportFile = new File(reportsDir, msisdn + "_" + UUID + ".csv");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFile))) {
            for (CDRRecord cdr : cdrs) {
                bufferedWriter.write(cdr.toString());
                bufferedWriter.newLine();
            }
        }
    }
}
