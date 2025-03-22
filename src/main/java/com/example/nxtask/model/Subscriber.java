package com.example.nxtask.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Класс абонента, хранящий его номер
 */
@Entity
@Table(name = "subscribers")
public class Subscriber {
    /**
     * Регулярное выражение для валидации номера
     */
    @Transient
    private final static String regex = "7\\d{10}";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String number;

    public Subscriber() {
    }

    public Subscriber(String number) {
        if (!Pattern.matches(regex, number)) {
            throw new IllegalArgumentException("Номер телефона должен состоять из 7 + 10 арабских цифр");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (!Pattern.matches(regex, number)) {
            throw new IllegalArgumentException("Номер телефона должен состоять из 7 + 10 арабских цифр");
        }
        this.number = number;
    }
}
