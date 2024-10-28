package org.example.spring4;

import org.example.spring4.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class Spring45Application implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(Spring45Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Osoby zdefiniowane w beans.xml:");
        personService.processPersonsFromBeans();

        System.out.println("\nPracownicy z pliku CSV:");
        String csvFilePath = "src/main/resources/MOCK_DATA.csv";
        personService.loadPersonsFromCSV(csvFilePath);
        personService.getPersons().forEach(System.out::println);

        System.out.println("\nPracownicy z firmy Twitterbridge:");
        personService.filterByCompany("Twitterbridge").forEach(System.out::println);

        System.out.println("\nPracownicy posortowani według nazwiska:");
        personService.sortByLastName().forEach(System.out::println);
    }
}
