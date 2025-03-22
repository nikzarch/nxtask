package com.example.nxtask.util;

import com.example.nxtask.model.CDRRecord;
import com.example.nxtask.model.Subscriber;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестов, покрывающий код генератора
 */
public class CDRGeneratorTest {
    /**
     * Метод, тестирующий генерацию записей, проверяющий количество сгенерированных абонентов и сортировку в хронологическом порядке
     */
    @Test
    void testGenerateCDRRecords(){
        List<Subscriber> subs = CDRGenerator.getRandomSubscribers(10);
        CDRGenerator cdrGenerator = new CDRGenerator(subs);

        List<CDRRecord> cdrRecords = cdrGenerator.generateCDRRecords(10);

        assertEquals(10, cdrRecords.size());

        for (int i = 1; i < cdrRecords.size();i++){
            Instant prev = cdrRecords.get(i - 1).getStart();
            Instant curr = cdrRecords.get(i).getStart();
            assertFalse(prev.isAfter(curr), "Записи не отсортированы по возрастанию времени начала");
        }
    }

    /**
     * Метод, тестирующий формат генерируемых номеров телефона
     */
    @Test
    void testGenerateRandomSubscribersFormat() {
        List<Subscriber> subs = CDRGenerator.getRandomSubscribers(10);
        for (Subscriber sub : subs) {
            String number = sub.getNumber();
            assertTrue(number.startsWith("7"));
            assertEquals(11, number.length());
        }
    }
}
