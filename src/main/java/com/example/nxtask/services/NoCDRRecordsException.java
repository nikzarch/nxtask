package com.example.nxtask.services;

/**
 * Исключение, происходящее в ошибке бизнес-логики, когда записи не найдены
 */
public class NoCDRRecordsException extends Exception {
    public NoCDRRecordsException(String message) {
        super(message);
    }
}
