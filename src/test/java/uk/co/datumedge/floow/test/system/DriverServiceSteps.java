package uk.co.datumedge.floow.test.system;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.co.datumedge.floow.DriverServiceApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DriverServiceSteps {
    private ConfigurableApplicationContext applicationContext;

    @BeforeScenario
    public void start() throws IOException {
        Files.write(Paths.get("testdb.csv"), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
        applicationContext = SpringApplication.run(DriverServiceApplication.class,
                "--csvFile=testdb.csv", "--fixedEpochClock");
    }

    @AfterScenario
    public void stop() {
        if (applicationContext != null) applicationContext.stop();
    }
}
