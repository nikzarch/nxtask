package com.example.nxtask.services;

import com.example.nxtask.db.CDRRecordRepository;
import com.example.nxtask.model.CDRRecord;
import com.example.nxtask.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с CDR
 * <p>
 * Предоставляет методы для получения данных о звонках абонентов в период времени.
 */
@Service
public class CDRService {
    @Autowired
    private final CDRRecordRepository cdrRecordRepository;

    @Autowired
    private final FileManager fileManager;

    public CDRService(CDRRecordRepository cdrRecordRepository, FileManager fileManager) {
        this.cdrRecordRepository = cdrRecordRepository;
        this.fileManager = fileManager;
    }

    public UUID getAndSaveCDRByNumberAndBetweenDate(String msisdn, Instant from, Instant to) throws IOException, NoCDRRecordsException {
        List<CDRRecord> cdrs = cdrRecordRepository.findByCallerNumberAndPeriod(msisdn, from, to);
        cdrs.addAll(cdrRecordRepository.findByAnswererNumberAndPeriod(msisdn, from, to));
        UUID uuid = UUID.randomUUID();
        if (cdrs.isEmpty()) {
            throw new NoCDRRecordsException("Записей не найдено");
        }
        fileManager.saveCDRToCSV(cdrs, uuid.toString(), msisdn);
        return uuid;
    }
}
