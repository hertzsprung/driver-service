package uk.co.datumedge.floow;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class DriverServiceSteps {
    private ConfigurableApplicationContext applicationContext;

    @BeforeScenario
    public void startService() {
        applicationContext = SpringApplication.run(DriverServiceApplication.class);
    }

    @AfterScenario
    public void stopService() {
        if (applicationContext != null) applicationContext.stop();
    }
}
