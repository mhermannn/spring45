package org.example.spring4.service;

import org.example.spring4.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final CompanyFix companyFix;
    private final List<Person> persons;
    private int currentId = 1;

    @Autowired
    public PersonService(CompanyFix companyFix,
                         @Qualifier("president") Person president,
                         @Qualifier("vicePresident") Person vicePresident,
                         @Qualifier("secretary") Person secretary) {
        this.companyFix = companyFix;
        this.persons = new ArrayList<>();
        president.setId(currentId++);
        vicePresident.setId(currentId++);
        secretary.setId(currentId++);
        persons.add(president);
        persons.add(vicePresident);
        persons.add(secretary);
    }

    public List<Person> getAllPersons() {
        return persons;
    }

    public Optional<Person> getPersonById(int id) {
        return persons.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Person addPerson(Person person) {
        person.setId(currentId++);
        persons.add(person);
        return person;
    }

    public Person updatePerson(int id, Person updatedPerson) {
        Optional<Person> existingPerson = getPersonById(id);
        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setEmail(updatedPerson.getEmail());
            person.setCompany(updatedPerson.getCompany());
            return person;
        } else {
            throw new NoSuchElementException("Person not found with ID: " + id);
        }
    }

    public boolean deletePerson(int id) {
        return persons.removeIf(person -> person.getId() == id);
    }

    public List<Person> filterByCompany(String companyName) {
        return persons.stream()
                .filter(person -> person.getCompany().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    public List<Person> sortByLastName() {
        return persons.stream()
                .sorted(Comparator.comparing(Person::getLastName))
                .collect(Collectors.toList());
    }

    public void processPersonsFromBeans() {
        persons.forEach(person -> System.out.println("Processing Person from Bean: " + person));
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void loadPersonsFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String firstName = data[0];
                String lastName = data[1];
                String email = data[2];
                String company = companyFix.extractCompanyFromEmail(email);
                Person person = new Person(firstName, lastName, email, company);
                addPerson(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
