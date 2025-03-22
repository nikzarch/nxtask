package com.example.nxtask.model;

import java.time.Duration;
/**
 * UDR (Usage Data Record)
 */
public class UDR {
    private String msisdn;
    private CallTime incomingCall;
    private CallTime outcomingCall;
    /**
     * Конструктор для создания UDR.
     *
     * @param msisdn  номер абонента
     * @param incomingCall время входящих звонков
     * @param outcomingCall  время исходящих звонков
     */
    public UDR(String msisdn, CallTime incomingCall, CallTime outcomingCall) {
        this.msisdn = msisdn;
        this.incomingCall = incomingCall;
        this.outcomingCall = outcomingCall;
    }
    /**
     * Конструктор для создания UDR.
     *
     * @param msisdn  номер абонента
     * @param incomingCall время входящих звонков
     * @param outcomingCall  время исходящих звонков
     */
    public UDR(String msisdn, Duration incomingCall, Duration outcomingCall) {
        this.msisdn = msisdn;
        this.incomingCall = new CallTime(incomingCall);
        this.outcomingCall = new CallTime(outcomingCall);
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public CallTime getIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(CallTime incomingCall) {
        this.incomingCall = incomingCall;
    }

    public CallTime getOutcomingCall() {
        return outcomingCall;
    }

    public void setOutcomingCall(CallTime outcomingCall) {
        this.outcomingCall = outcomingCall;
    }

    /**
     * Класс для хранения времени звонка
     */
    public static class CallTime {
        private final String totalTime;

        public CallTime(Duration duration) {
            this.totalTime = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
        }

        public String getTotalTime() {
            return totalTime;
        }
    }
}
