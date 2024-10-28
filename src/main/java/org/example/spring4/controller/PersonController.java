package org.example.spring4.controller;

import org.example.spring4.domain.Person;
import org.example.spring4.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Optional<Person> getPersonById(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable int id, @RequestBody Person updatedPerson) {
        return personService.updatePerson(id, updatedPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }
}
