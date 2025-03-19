package com.example.nxtask.model;

import java.time.Instant;


/**
 * Класс для CDR записи
 * CDR-запись включает в себя следующие данные:
 *   тип вызова (01 - исходящие, 02 - входящие);
 *  номер абонента, инициирующего звонок;
 *  номер абонента, принимающего звонок;
 * дата и время начала звонка (ISO 8601);
 *  дата и время окончания звонка (ISO 8601);
 */

public class CDRRecord {

    private CallType callType;
    private Subscriber caller;
    private Subscriber answerer;

    private Instant start;
    private Instant end;

    public CDRRecord(CallType callType, Subscriber caller, Subscriber answerer, Instant start, Instant end){
        this.callType = callType;
        this.caller = caller;
        this.answerer = answerer;
        if (start.isAfter(end)){
            throw new IllegalArgumentException("Невалидные даты начала и конца");
        }
        this.start = start;
        this.end = end;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public void setCaller(Subscriber caller) {
        this.caller = caller;
    }

    public void setAnswerer(Subscriber answerer) {
        this.answerer = answerer;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public CallType getCallType() {
        return callType;
    }

    public Subscriber getCaller() {
        return caller;
    }

    public Subscriber getAnswerer() {
        return answerer;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return this.callType.getType() +',' + this.caller.getNumber()+ ',' +this.answerer.getNumber()+',' +this.start+',' +this.end;
    }
}
