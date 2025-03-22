package com.example.nxtask.model;

import jakarta.persistence.*;

import java.time.Instant;


/**
 * Класс-сущность CDR записи
 */
@Entity
@Table(name = "cdr_records")
public class CDRRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * тип вызова (01 - исходящие, 02 - входящие);
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CallType callType;
    /**
     * номер абонента, инициирующего звонок;
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "caller_id", nullable = false)
    private Subscriber caller;
    /**
     * номер абонента, принимающего звонок;
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "answerer_id", nullable = false)
    private Subscriber answerer;
    /**
     * дата и время начала звонка (ISO 8601);
     */
    @Column(nullable = false)
    private Instant startTime;
    /**
     * дата и время окончания звонка (ISO 8601);
     */
    @Column(nullable = false)
    private Instant endTime;

    public CDRRecord() {
    }

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

    public Long getId() {
        return id;
    }

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public Subscriber getCaller() {
        return caller;
    }

    public void setCaller(Subscriber caller) {
        this.caller = caller;
    }

    public Subscriber getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Subscriber answerer) {
        this.answerer = answerer;
    }

    public Instant getStart() {
        return startTime;
    }

    public void setStart(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEnd() {
        return endTime;
    }

    public void setEnd(Instant endTime) {
        this.endTime = endTime;
    }

    /**
     * Метод преобразования в строку
     *
     * @return строку вида "тип, номер звонящего, номер принимающего, дата и время начала звонка, дата и время конца звонка"
     */
    @Override
    public String toString() {
        return this.callType.getType() + ',' + this.caller.getNumber() + ',' + this.answerer.getNumber() + ',' + this.startTime + ',' + this.endTime;
    }
}
