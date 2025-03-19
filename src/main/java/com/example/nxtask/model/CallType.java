package com.example.nxtask.model;
/***
 *список типов звонков
 */
public enum CallType {
    OUTGOING("01","Входящий"),

    INCOMING("02","Исходящий");

    private final String type;
    private final String description;

    CallType(String type,String description) {
        this.type = type;
        this.description = description;
    }
    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
