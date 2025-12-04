package com.example.trial;

import com.example.trial.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TrialController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/message")
    public String getMessage(){
        return "Hello Mustafa";
    }

    @GetMapping("/person-list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(personRepository.findAll());
    }
}
