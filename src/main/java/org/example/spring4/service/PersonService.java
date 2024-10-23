package org.example.spring4.service;

import org.example.spring4.domain.Person;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.Comparator;


@Service
public class PersonService {
    public List<Person> filterByCompany(List<Person> persons, String companyName) {
        return persons.stream()
                .filter(person -> person.getCompany().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    public List<Person> sortByLastName(List<Person> persons) {
        return persons.stream()
                .sorted(Comparator.comparing(Person::getLastName))
                .collect(Collectors.toList());
    }
    private final CompanyFix companyFix;

    public PersonService(CompanyFix companyFix) {
        this.companyFix = companyFix;
    }

    public List<Person> loadPersonsFromCSV(String filePath) {
        List<Person> persons = new ArrayList<>();
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
        return persons;
    }

    public void processPersonsFromBeans(List<Person> persons) {
        for (Person person : persons) {
            System.out.println("Processing Person from Bean: " + person.toString());
        }
    }


}
