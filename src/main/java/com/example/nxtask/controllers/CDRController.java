package com.example.nxtask.controllers;


import com.example.nxtask.services.CDRService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

/**
 * REST-контроллер для работы с CDR
 */
@RestController
public class CDRController {
    private final CDRService cdrService;

    public CDRController(CDRService cdrService) {
        this.cdrService = cdrService;
    }

    @PostMapping("/api/cdr")
    public ResponseEntity<String> getCDRReport(@RequestParam String msisdn, @RequestParam Instant from, @RequestParam Instant to) {
        try {
            UUID uuid = cdrService.getAndSaveCDRByNumberAndBetweenDate(msisdn, from, to);
            return ResponseEntity.ok().body(uuid.toString());
        } catch (Exception exc) {
            return ResponseEntity.internalServerError().body(exc.getMessage());
        }

    }
}
