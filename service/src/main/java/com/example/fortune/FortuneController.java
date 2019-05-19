package com.example.fortune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fortune")
public class FortuneController {

    @Autowired
    private FortuneService fortuneService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFortune() {
        return ResponseEntity.status(HttpStatus.OK).body(fortuneService.getFortune());
    }
}
