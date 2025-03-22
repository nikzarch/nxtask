package com.example.nxtask.db;

import com.example.nxtask.model.CDRRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
/**
 *  JPA Репозиторий для работы с CDR-записями.
 */
@Repository
public interface CDRRecordRepository extends JpaRepository<CDRRecord, Long> {
    /**
     * Находит CDR-записи по номеру вызывающего абонента.
     *
     * @param callerNumber номер вызывающего абонента
     * @return список CDR-записей
     */
    List<CDRRecord> findByCallerNumber(String callerNumber);

    /**
     * Находит CDR-записи по номеру принимающего абонента.
     *
     * @param answererNumber номер принимающего абонента
     * @return список CDR-записей
     */
    List<CDRRecord> findByAnswererNumber(String answererNumber);

    List<CDRRecord> findByStartTimeBetween(Instant start, Instant end);
    /**
     * Находит CDR-записи по месяцу.
     *
     * @param month месяц (1-12)
     * @return список CDR-записей
     */
    @Query("SELECT record FROM CDRRecord record WHERE MONTH(record.startTime) = :month")
    List<CDRRecord> findByMonth(@Param("month") int month);

}
