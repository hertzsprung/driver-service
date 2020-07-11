package uk.co.datumedge.floow.test.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.util.SimpleIdGenerator;
import uk.co.datumedge.floow.Drivers;
import uk.co.datumedge.floow.repository.CSVDriverRepository;
import uk.co.datumedge.floow.repository.DriverRepository;

import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.datumedge.floow.test.TestDrivers.JESSICA_GREENE;
import static uk.co.datumedge.floow.test.TestDrivers.LUCAS_REES;

public class CSVDriverRepositoryTest {
    private static final Instant CREATION_INSTANT = EPOCH;

    @TempDir
    Path directory;

    @Test
    public void roundTripSingleDriver() {
        DriverRepository repository = csvRepository();

        repository.save(LUCAS_REES.build());
        Drivers actualDrivers = repository.findAll();

        Drivers expectedDrivers = new Drivers(LUCAS_REES.withId(1).createdAt(CREATION_INSTANT).build());

        assertThat(actualDrivers).isEqualTo(expectedDrivers);
    }

    @Test
    public void roundTripTwoDrivers() {
        DriverRepository repository = csvRepository();

        repository.save(LUCAS_REES.build());
        repository.save(JESSICA_GREENE.build());

        Drivers actualDrivers = repository.findAll();

        Drivers expectedDrivers = new Drivers(
                LUCAS_REES.withId(1).createdAt(CREATION_INSTANT).build(),
                JESSICA_GREENE.withId(2).createdAt(CREATION_INSTANT).build());

        assertThat(actualDrivers).isEqualTo(expectedDrivers);
    }

    private CSVDriverRepository csvRepository() {
        return new CSVDriverRepository(directory.resolve("db.csv"), UTF_8, Clock.fixed(CREATION_INSTANT, UTC),
                new SimpleIdGenerator());
    }
}
