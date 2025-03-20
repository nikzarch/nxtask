package com.example.nxtask.services;

import com.example.nxtask.db.CDRRecordRepository;
import com.example.nxtask.model.CDRRecord;
import com.example.nxtask.model.UDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class UDRService {

    @Autowired
    private final CDRRecordRepository cdrRecordRepository;

    public UDRService(CDRRecordRepository cdrRecordRepository) {
        this.cdrRecordRepository = cdrRecordRepository;
    }

    public UDR getUDRBySubscriber(String number, Integer month) {
        Duration incomingCall, outcomingCall;
        if (Objects.isNull(month)) {
            List<CDRRecord> CDRsForIncoming = cdrRecordRepository.findByCallerNumber(number);
            incomingCall = CDRsForIncoming.stream().map(cdrRecord -> Duration.between(cdrRecord.getStart(), cdrRecord.getEnd())).reduce(Duration.ZERO, Duration::plus);
            List<CDRRecord> CDRsForOutcoming = cdrRecordRepository.findByAnswererNumber(number);
            outcomingCall = CDRsForOutcoming.stream().map(cdrRecord -> Duration.between(cdrRecord.getStart(), cdrRecord.getEnd())).reduce(Duration.ZERO, Duration::plus);
        } else {
            List<CDRRecord> cdrs = cdrRecordRepository.findByMonth(month);
            List<CDRRecord> CDRsForIncoming = cdrs.stream().filter(cdrRecord -> cdrRecord.getCaller().getNumber().equals(number)).toList();
            List<CDRRecord> CDRsForOutcoming = cdrs.stream().filter(cdrRecord -> cdrRecord.getAnswerer().getNumber().equals(number)).toList();
            incomingCall = CDRsForIncoming.stream().map(cdrRecord -> Duration.between(cdrRecord.getStart(), cdrRecord.getEnd())).reduce(Duration.ZERO, Duration::plus);
            outcomingCall = CDRsForOutcoming.stream().map(cdrRecord -> Duration.between(cdrRecord.getStart(), cdrRecord.getEnd())).reduce(Duration.ZERO, Duration::plus);
        }
        return new UDR(number, incomingCall, outcomingCall);
    }

    public List<UDR> getAllUDRByMonth(Integer month) {
        List<CDRRecord> cdrs = cdrRecordRepository.findByMonth(month);
        Map<String, Duration[]> callDurations = new HashMap<>();

        for (CDRRecord cdr : cdrs) {
            String callerNumber = cdr.getCaller().getNumber();
            String answererNumber = cdr.getAnswerer().getNumber();
            Duration callDuration = Duration.between(cdr.getStart(), cdr.getEnd());

            callDurations.putIfAbsent(callerNumber, new Duration[]{Duration.ZERO, Duration.ZERO});
            callDurations.get(callerNumber)[0] = callDurations.get(callerNumber)[0].plus(callDuration); // incomingCall++

            callDurations.putIfAbsent(answererNumber, new Duration[]{Duration.ZERO, Duration.ZERO});
            callDurations.get(answererNumber)[1] = callDurations.get(answererNumber)[1].plus(callDuration); // outcomingCall++
        }

        List<UDR> udrs = new ArrayList<>();
        for (Map.Entry<String, Duration[]> entry : callDurations.entrySet()) {
            udrs.add(new UDR(entry.getKey(), entry.getValue()[1], entry.getValue()[0]));
        }

        return udrs;
    }
}
