package uk.co.datumedge.floow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.UTC;

@SpringBootApplication
@Configuration
public class DriverServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DriverServiceApplication.class, args);
    }

    @Bean
    public DriverRepository driverRepository(Path csvDriverRepositoryPath, Clock clock) {
        return new CSVDriverRepository(csvDriverRepositoryPath, UTF_8, clock);
    }

    @Bean
    public Path csvDriverRepositoryPath(ApplicationArguments args) {
        if (args.containsOption("csvFile") && args.getOptionValues("csvFile").size() > 0) {
            return Paths.get(args.getOptionValues("csvFile").get(0));
        } else {
            return Paths.get("db.csv");
        }
    }

    @Bean
    Clock clock(ApplicationArguments args) {
        if (args.containsOption("fixedEpochClock")) {
            return Clock.fixed(EPOCH, UTC);
        } else {
            return Clock.systemUTC();
        }
    }
}
