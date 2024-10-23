package org.example.spring4;

import org.example.spring4.domain.Person;
import org.example.spring4.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class Spring4Application implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    @Qualifier("president")
    private Person president;

    @Autowired
    @Qualifier("vicePresident")
    private Person vicePresident;

    @Autowired
    @Qualifier("secretary")
    private Person secretary;

    public static void main(String[] args) {
        SpringApplication.run(Spring4Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Osoby zdefiniowane w beans.xml:");
        List<Person> beansPersons = new ArrayList<>();
        beansPersons.add(president);
        beansPersons.add(vicePresident);
        beansPersons.add(secretary);
        personService.processPersonsFromBeans(beansPersons);

        System.out.println("\nPracownicy z pliku CSV:");
        String csvFilePath = "src/main/resources/MOCK_DATA.csv";
        List<Person> persons = personService.loadPersonsFromCSV(csvFilePath);
        persons.forEach(System.out::println);

        System.out.println("\nPracownicy z firmy Twitterbridge:");
        List<Person> twitterbridgeEmployees = personService.filterByCompany(persons, "Twitterbridge");
        twitterbridgeEmployees.forEach(System.out::println);

        System.out.println("\nPracownicy posortowani według nazwiska:");
        List<Person> sortedPersons = personService.sortByLastName(persons);
        sortedPersons.forEach(System.out::println);
    }
}
