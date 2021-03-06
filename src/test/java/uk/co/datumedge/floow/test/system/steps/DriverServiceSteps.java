package uk.co.datumedge.floow.test.system.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;
import uk.co.datumedge.floow.DriverServiceApplication;
import uk.co.datumedge.floow.test.TestableClock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;

public class DriverServiceSteps {
    private ConfigurableApplicationContext applicationContext;

    @BeforeScenario
    public void start() throws IOException {
        Files.deleteIfExists(Paths.get("target/testdb.csv"));
        SpringApplication springApplication = new SpringApplication(DriverServiceApplication.class, Configuration.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        applicationContext = springApplication.run("--csvFile=target/testdb.csv", "--testableClock");
    }

    @AfterScenario
    public void stop() {
        if (applicationContext != null) applicationContext.stop();
    }

    public static class Configuration {
        @Bean
        public Clock clock() {
            return new TestableClock();
        }

        @Bean
        public IdGenerator idGenerator() {
            return new SimpleIdGenerator();
        }
    }
}
