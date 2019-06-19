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

    /**
     * Service object that returns fortunes.
     */
    @Autowired
    private FortuneService fortuneService;

    /**
     * Return a random fortune as a json payload.
     *
     * @return response
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFortune() {
        return ResponseEntity.status(HttpStatus.OK).body(fortuneService.getFortune());
    }
}
