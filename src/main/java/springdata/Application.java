package springdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    private List<Customer> buildInitialData() {
        return new ArrayList<Customer>() {{
            add(new Customer("Jack", "Bauer"));
            add(new Customer("Chloe", "O'Brian"));
            add(new Customer("Kim", "Bauer"));
            add(new Customer("David", "Palmer"));
            add(new Customer("Michelle", "Dessler"));
            add(new Customer("Alex", "BR"));
        }};
    }


    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            buildInitialData().forEach(repository::save);
            repository.save(new Customer("ALEX", "BR1"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("Customer found with findByLastNameIgnoreCaseContaining('b'):");
            log.info("---------------------------------------------------------");
            repository.findByLastNameIgnoreCaseContaining("b").forEach(b -> {
                log.info(b.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}