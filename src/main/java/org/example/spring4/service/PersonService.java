package org.example.spring4.service;

import org.example.spring4.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final CompanyFix companyFix;

    private final List<Person> persons;

    @Autowired
    public PersonService(CompanyFix companyFix,
                         @Qualifier("president") Person president,
                         @Qualifier("vicePresident") Person vicePresident,
                         @Qualifier("secretary") Person secretary) {
        this.companyFix = companyFix;
        this.persons = new ArrayList<>();
        persons.add(president);
        persons.add(vicePresident);
        persons.add(secretary);
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
                persons.add(new Person(firstName, lastName, email, company));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
