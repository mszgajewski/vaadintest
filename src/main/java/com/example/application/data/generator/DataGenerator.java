package com.example.application.data.generator;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.StatusRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {

    public CommandLineRunner lineRunner(ContactRepository contactRepository, CompanyRepository companyRepository,
                                        StatusRepository statusRepository){
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if(contactRepository.count() != 0L) {
                logger.info("Użyj bazy danych");
                return;
            }
            int seed = 123;

            logger.info("Generowanie danch");

            ExampleDataGenerator<Company> companyGenerator = new ExampleDataGenerator<>(Company.class,
                    LocalDateTime.now());

            companyGenerator.setData(Company::setName, DataType.COMPANY_NAME);

            List<Company> companies = companyRepository.saveAll(companyGenerator.create(2,seed));

            List <Status> statuses = statusRepository.saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed(lost)")
                    .map(Status::new).collect(Collectors.toList()));

            logger.info("... generating 50 Contact entities...");
            ExampleDataGenerator<Contact> contactGenerator = new ExampleDataGenerator<>(Contact.class,
                    LocalDateTime.now());
            contactGenerator.setData(Contact::setFirstName, DataType.FIRST_NAME);
            contactGenerator.setData(Contact::setLastName, DataType.LAST_NAME);
            contactGenerator.setData(Contact::setEmail, DataType.EMAIL);

            Random random = new Random(seed);

            List<Contact> contacts = contactGenerator.create(50, seed).stream().peek(contact -> {
                contact.setCompany(companies.get(random.nextInt(companies.size())));
                contact.setStatus(statuses.get(random.nextInt(statuses.size())));
            }).collect(Collectors.toList());

            contactRepository.saveAll(contacts);

            logger.info("Wygenerowano dane");
        };
    }
}
