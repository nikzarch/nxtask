package com.example.nxtask.generator;

import com.example.nxtask.model.CDRRecord;
import com.example.nxtask.model.CallType;
import com.example.nxtask.model.Subscriber;

import java.time.Instant;
import java.util.*;

/**
 * Класс генератор CDR записей
 */
public class CDRGenerator {
    private List<Subscriber> subscribers;

    public CDRGenerator(List<Subscriber> subscribers) {
        this.subscribers = new ArrayList<>(subscribers);
    }

    public List<CDRRecord> generateCDRRecords(int amount) {
        List<CDRRecord> records = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            CallType type = getRandomCallType();
            Subscriber[] subscribers = getRandomCallerAndAnswerer();
            Instant[] startEnd = getRandomStartAndEnd();
            CDRRecord record = new CDRRecord(type, subscribers[0], subscribers[1], startEnd[0], startEnd[1]);
            records.add(record);
        }
        records.sort(Comparator.comparing(CDRRecord::getStart));
        return records;
    }

    public List<CDRRecord> generateCDRRecords(List<Subscriber> subscribers, int amount) {
        List<CDRRecord> records = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            CallType type = getRandomCallType();
            Subscriber caller, answerer;

            do {
                caller = subscribers.get(random.nextInt(subscribers.size()));
                answerer = subscribers.get(random.nextInt(subscribers.size()));
            } while (caller.equals(answerer));

            Instant[] startEnd = getRandomStartAndEnd();
            CDRRecord record = new CDRRecord(type, caller, answerer, startEnd[0], startEnd[1]);
            records.add(record);
        }
        records.sort(Comparator.comparing(CDRRecord::getStart));
        return records;
    }


    protected Instant[] getRandomStartAndEnd() {
        Instant now = Instant.now();
        long maxOffsetSeconds = 365L * 24 * 60 * 60; // 1 год в секундах
        Random random = new Random();
        long randomOffset = (long) (random.nextDouble() * maxOffsetSeconds);
        Instant start = now.minusSeconds(randomOffset);

        long callDuration = 10 + random.nextInt(3600 - 10);
        Instant end = start.plusSeconds(callDuration);
        return new Instant[]{start, end};
    }

    protected CallType getRandomCallType() {
        Random random = new Random();
        CallType[] values = CallType.values();
        return values[random.nextInt(values.length)];
    }

    protected Subscriber[] getRandomCallerAndAnswerer() {
        Random random = new Random();
        StringBuilder firstNumber = new StringBuilder("7");
        StringBuilder secondNumber = new StringBuilder("7");
        for (int i = 0; i < 10; i++) {
            firstNumber.append(random.nextInt(10));
            secondNumber.append(random.nextInt(10));
        }
        return new Subscriber[]{new Subscriber(firstNumber.toString()), new Subscriber(secondNumber.toString())};
    }

    public static List<Subscriber> getRandomSubscribers(int amount) {
        Random random = new Random();
        Set<String> uniqueNumbers = new HashSet<>();
        while (uniqueNumbers.size() < amount) {
            String number = generateRandomNumber(random);
            uniqueNumbers.add(number);
        }
        return uniqueNumbers.stream().map(Subscriber::new).toList();
    }

    private static String generateRandomNumber(Random random) {
        StringBuilder number = new StringBuilder("7");
        for (int i = 0; i < 10; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }
}
