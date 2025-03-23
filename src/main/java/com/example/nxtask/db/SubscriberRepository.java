package com.example.nxtask.db;

import com.example.nxtask.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Репозиторий для работы с абонентами
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    /**
     * Находит абонента по номеру
     *
     * @param number номер абонента
     * @return Объект Subscriber абонента
     */
    Subscriber findByNumber(String number);

}
