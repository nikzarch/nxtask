package com.example.nxtask;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/arr")
    public String arbuz(){
        return "ARBUZZZZZ";
    }
}
