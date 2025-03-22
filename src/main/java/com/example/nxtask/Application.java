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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        //temp
        List<Subscriber> subscribers = CDRGenerator.getRandomSubscribers(10);
        subscriberRepository.saveAll(subscribers);

        CDRGenerator generator = new CDRGenerator(subscribers);
        List<CDRRecord> records = generator.generateCDRRecords(50);
        cdrRecordRepository.saveAll(records);
        cdrRecordRepository.findAll().forEach(System.out::println);

    }
}

