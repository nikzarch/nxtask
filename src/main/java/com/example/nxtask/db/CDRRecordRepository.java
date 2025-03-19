package com.example.nxtask.db;

import com.example.nxtask.model.CDRRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CDRRecordRepository extends JpaRepository<CDRRecord, Long> {
    List<CDRRecord> findByCallerNumber(String callerNumber);
    List<CDRRecord> findByAnswererNumber(String answererNumber);
    List<CDRRecord> findByStartTimeBetween(Instant start, Instant end);
}
