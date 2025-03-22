package com.example.nxtask.controllers;

import com.example.nxtask.db.CDRRecordRepository;
import com.example.nxtask.db.SubscriberRepository;
import com.example.nxtask.model.UDR;
import com.example.nxtask.services.UDRService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс тестов, покрывающий код контроллера
 */
@WebMvcTest(UDRController.class)
public class UDRControllerTest {
    @Autowired
    private MockMvc mock;

    @MockitoBean
    private UDRService udrService;
    @MockitoBean
    private SubscriberRepository subscriberRepository;
    @MockitoBean
    private CDRRecordRepository cdrRecordRepository;

    /**
     * Тест контроллера - вывод отчета по абоненту
     * @throws Exception
     */
    @Test
    void testGetUDRBySubscriber() throws Exception {
        String msisdn = "71234567890";
        long expectedCallTime = 5 * 60;
        long expectedBillingTime = 3 * 60;
        UDR udr = new UDR(msisdn, new UDR.CallTime(Duration.ofSeconds(expectedCallTime)), new UDR.CallTime(Duration.ofSeconds(expectedBillingTime)));
        Mockito.when(udrService.getUDRBySubscriber(eq(msisdn), Mockito.isNull())).thenReturn(udr);

        mock.perform(get("/api/udr")
                        .param("msisdn", msisdn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msisdn").value(msisdn))
                .andExpect(jsonPath("$.incomingCall.totalTime").value("00:05:00"))
                .andExpect(jsonPath("$.outcomingCall.totalTime").value("00:03:00"));
    }
}
