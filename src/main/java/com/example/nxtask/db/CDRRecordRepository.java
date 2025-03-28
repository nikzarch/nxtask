package com.example.nxtask.db;

import com.example.nxtask.model.CDRRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * JPA Репозиторий для работы с CDR-записями.
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

    /**
     * Находит CDR записи исходящих звонков абонента за определенный период
     *
     * @return Лист CDR записей абонента за период
     */
    @Query("SELECT c FROM CDRRecord c WHERE c.caller.number = :msisdn AND c.startTime BETWEEN :from AND :to")
    List<CDRRecord> findByCallerNumberAndPeriod(@Param("msisdn") String msisdn, @Param("from") Instant from, @Param("to") Instant to);

    /**
     * Находит CDR записи входящих звонков абонента за определенный период
     *
     * @return Лист CDR записей абонента за период
     */
    @Query("SELECT c FROM CDRRecord c WHERE c.answerer.number = :msisdn AND c.startTime BETWEEN :from AND :to")
    List<CDRRecord> findByAnswererNumberAndPeriod(@Param("msisdn") String msisdn, @Param("from") Instant from, @Param("to") Instant to);

}
