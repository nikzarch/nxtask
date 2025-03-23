package com.example.nxtask;

import com.example.nxtask.db.CDRRecordRepository;
import com.example.nxtask.db.SubscriberRepository;
import com.example.nxtask.model.CDRRecord;
import com.example.nxtask.model.Subscriber;
import com.example.nxtask.util.CDRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Основной класс приложения
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private SubscriberRepository subscriberRepository;
    @Autowired
    private CDRRecordRepository cdrRecordRepository;
    @Autowired
    private CDRGenerator cdrGenerator;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Integer numberOfRecords, numberOfSubscribers;
        try {
            numberOfRecords = Integer.parseInt(args[0]);
            numberOfSubscribers = Integer.parseInt(args[1]);
            if (numberOfRecords <= 0) {
                throw new IllegalArgumentException("arg1 must be bigger than zero");
            }
            if (numberOfSubscribers <= 1) {
                throw new IllegalArgumentException("arg2 must be bigger than one");
            }
        } catch (Exception exc) {
            System.out.println("Параметры количество записей и абонентов установлены на стандартное значение");
            numberOfRecords = 1000;
            numberOfSubscribers = 10;
        }
        List<Subscriber> subscribers = CDRGenerator.getRandomSubscribers(numberOfSubscribers);
        subscriberRepository.saveAll(subscribers);
        subscriberRepository.findAll().stream().map(Subscriber::getNumber).forEach(System.out::println);
        cdrGenerator.setSubscribers(subscribers);
        List<CDRRecord> records = cdrGenerator.generateCDRRecords(numberOfRecords);
        cdrRecordRepository.saveAll(records);
        cdrRecordRepository.findAll().forEach(System.out::println); //temp
    }
}

