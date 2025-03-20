package com.example.nxtask.controllers;

import com.example.nxtask.model.UDR;
import com.example.nxtask.services.UDRService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/udr")
public class UDRController {
    private final UDRService udrService;

    public UDRController(UDRService udrService) {
        this.udrService = udrService;
    }

    @GetMapping
    public UDR getUDRBySubscriber(@RequestParam String msisdn, @RequestParam(required = false) Integer month) {
        return udrService.getUDRBySubscriber(msisdn, month);
    }

    @GetMapping(params = "!msisdn")
    public List<UDR> getAllUDRByMonth(@RequestParam Integer month) {
        return udrService.getAllUDRByMonth(month);
    }


}
