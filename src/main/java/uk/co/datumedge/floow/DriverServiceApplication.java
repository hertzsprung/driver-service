package uk.co.datumedge.floow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootApplication
@Configuration
public class DriverServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DriverServiceApplication.class, args);
    }

    @Bean
    public DriverRepository driverRepository(ApplicationArguments args) {
        String filename = "db.csv";
        if (args.containsOption("csvFile") && args.getOptionValues("csvFile").size() > 0) {
            filename = args.getOptionValues("csvFile").get(0);
        }

        return new CSVDriverRepository(Paths.get(filename), UTF_8);
    }
}
