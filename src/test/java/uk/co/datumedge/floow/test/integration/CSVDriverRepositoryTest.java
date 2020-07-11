package uk.co.datumedge.floow.test.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import uk.co.datumedge.floow.CSVDriverRepository;
import uk.co.datumedge.floow.DriverRepository;
import uk.co.datumedge.floow.Drivers;

import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.datumedge.floow.test.TestDrivers.JESSICA_GREENE;
import static uk.co.datumedge.floow.test.TestDrivers.LUCAS_REES;

public class CSVDriverRepositoryTest {
    @TempDir
    Path directory;

    @Test
    public void roundTripSingleDriver() {
        DriverRepository repository = csvRepository();

        repository.save(LUCAS_REES);
        Drivers actualDrivers = repository.findAll();

        assertThat(actualDrivers).isEqualTo(new Drivers(LUCAS_REES));
    }

    @Test
    public void roundTripTwoDrivers() {
        DriverRepository repository = csvRepository();

        repository.save(LUCAS_REES);
        repository.save(JESSICA_GREENE);

        Drivers actualDrivers = repository.findAll();

        assertThat(actualDrivers).isEqualTo(new Drivers(LUCAS_REES, JESSICA_GREENE));
    }

    private CSVDriverRepository csvRepository() {
        return new CSVDriverRepository(directory.resolve("db.csv"), UTF_8);
    }
}
