package com.example.trial;

import com.example.trial.models.Person;
import com.example.trial.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

//@Component
public class SeedData {
    private final PersonRepository personRepository;

    public SeedData(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void initializeData() {
        if (personRepository.count() == 0) {
            Person p1 = new Person("Mustafa", "Guler");
            Person p2 = new Person("Ayse", "Yilmaz");

            personRepository.save(p1);
            personRepository.save(p2);

            System.out.println("Seed data inserted via @PostConstruct!");
        }
    }
}
