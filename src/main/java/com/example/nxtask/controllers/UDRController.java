package com.example.nxtask.controllers;

import com.example.nxtask.model.UDR;
import com.example.nxtask.services.UDRService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для работы с UDR (Usage Data Records).
 */
@RestController
@RequestMapping("/api/udr")
public class UDRController {
    private final UDRService udrService;

    public UDRController(UDRService udrService) {
        this.udrService = udrService;
    }

    /**
     * Отдаёт UDR-запись для конкретного абонента на GET запрос.
     *
     * @param msisdn номер абонента
     * @param month  месяц для фильтрации (необязательный параметр)
     * @return UDR-запись абонента
     */
    @GetMapping
    public UDR getUDRBySubscriber(@RequestParam String msisdn, @RequestParam(required = false) Integer month) {
        if (month != null && (month < 1 || month > 12)) {
            throw new IllegalArgumentException("Ошибка: месяц должен быть от 1 до 12.");
        }

        return udrService.getUDRBySubscriber(msisdn, month);
    }

    /**
     * Отдаёт все UDR-записи за указанный месяц на GET запрос.
     *
     * @param month месяц для фильтрации
     * @return список UDR-записей
     */
    @GetMapping(params = "!msisdn")
    public List<UDR> getAllUDRByMonth(@RequestParam Integer month) {
        if (month != null && (month < 1 || month > 12)) {
            throw new IllegalArgumentException("Ошибка: месяц должен быть от 1 до 12.");
        }
        return udrService.getAllUDRByMonth(month);
    }


}
