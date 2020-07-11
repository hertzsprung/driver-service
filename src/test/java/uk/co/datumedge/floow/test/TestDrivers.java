package uk.co.datumedge.floow.test;

import uk.co.datumedge.floow.Driver;

import java.time.LocalDate;

public class TestDrivers {
    public static final Driver.Builder LUCAS_REES = new Driver.Builder("Lucas", "Rees", LocalDate.of(1980, 5, 1));
    public static final Driver.Builder JESSICA_GREENE = new Driver.Builder("Jessica", "Greene", LocalDate.of(1974, 2, 23));
}
