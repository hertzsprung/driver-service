package uk.co.datumedge.floow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import uk.co.datumedge.floow.repository.CSVDriverRepository;
import uk.co.datumedge.floow.repository.DriverRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;

import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootApplication
@Configuration
public class DriverServiceApplication {
    /**
     * Start the driver service.
     * @param args Available configuration options are:
     *             <dl>
     *              <dt><code>--csvFile=&lt;path&gt;</code></dt>
     *              <dd>Specifies the path for the {@link CSVDriverRepository}
     *              (default is the relative path <code>db.csv</code>).</dd>
     *             </dl>
     */
    public static void main(String[] args) {
        SpringApplication.run(DriverServiceApplication.class, args);
    }

    @Bean
    public DriverRepository driverRepository(Path csvDriverRepositoryPath, Clock clock, IdGenerator idGenerator) {
        return new CSVDriverRepository(csvDriverRepositoryPath, UTF_8, clock, idGenerator);
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
    public Clock clock(ApplicationArguments args) {
        return Clock.systemUTC();
    }

    @Bean
    public IdGenerator idGenerator(ApplicationArguments args) {
        return new AlternativeJdkIdGenerator();
    }
}
