package uk.co.datumedge.floow.test;

import uk.co.datumedge.floow.Driver;

import java.time.Instant;

public class TestDrivers {
    public static class DriverBuilder {
        private final Driver driver;

        public DriverBuilder(String firstName, String lastName) {
            this.driver = new Driver(firstName, lastName);
        }

        public DriverBuilder createdAt(Instant instant) {
            this.driver.setCreated(instant);
            return this;
        }

        public Driver build() {
            return driver;
        }
    }

    public static final DriverBuilder LUCAS_REES = new DriverBuilder("Lucas", "Rees");
    public static final DriverBuilder JESSICA_GREENE = new DriverBuilder("Jessica", "Greene");
}
