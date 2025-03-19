package com.example.nxtask.model;

import jakarta.persistence.*;

import java.time.Instant;


/**
 * Класс для CDR записи
 * CDR-запись включает в себя следующие данные:
 * тип вызова (01 - исходящие, 02 - входящие);
 * номер абонента, инициирующего звонок;
 * номер абонента, принимающего звонок;
 * дата и время начала звонка (ISO 8601);
 * дата и время окончания звонка (ISO 8601);
 */
@Entity
@Table(name = "cdr_records")
public class CDRRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CallType callType;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "caller_id", nullable = false)
    private Subscriber caller;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answerer_id", nullable = false)
    private Subscriber answerer;

    @Column(nullable = false)
    private Instant startTime;
    @Column(nullable = false)
    private Instant endTime;

    public CDRRecord() {}
    public CDRRecord(CallType callType, Subscriber caller, Subscriber answerer, Instant startTime, Instant endTime) {
        this.callType = callType;
        this.caller = caller;
        this.answerer = answerer;
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Невалидные даты начала и конца");
        }
        this.startTime = startTime;
        this.endTime = endTime;
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

    public void setStart(Instant startTime) {
        this.startTime = startTime;
    }

    public void setEnd(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
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
        return startTime;
    }

    public Instant getEnd() {
        return endTime;
    }

    @Override
    public String toString() {
        return this.callType.getType() + ',' + this.caller.getNumber() + ',' + this.answerer.getNumber() + ',' + this.startTime + ',' + this.endTime;
    }
}
