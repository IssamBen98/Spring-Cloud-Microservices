package org.sid.customer_service;

import org.sid.customer_service.entities.Customer;
import org.sid.customer_service.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository cr) {
        return args -> {
            Stream.of("Jawad", "Mohammad", "Sanae", "Ali").forEach(c -> {
                Customer customer = new Customer();
                customer.setEmail(c.toLowerCase() + "@gmail.com");
                customer.setName(c.toUpperCase());
                cr.save(customer);
            });
        };
    }

}
