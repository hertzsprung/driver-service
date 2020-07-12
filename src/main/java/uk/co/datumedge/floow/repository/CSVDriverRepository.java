package uk.co.datumedge.floow.repository;

import org.springframework.util.IdGenerator;
import uk.co.datumedge.floow.model.Driver;
import uk.co.datumedge.floow.model.Drivers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.*;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.function.Function.identity;

/**
 * Stores {@link Drivers} in a CSV file.  The CSV file is formatted as:
 * <pre>
 *     id,firstname,lastname,date_of_birth,creationInstant
 *     ...
 * </pre>
 * with no header line and no quoted fields.
 * The <code>id</code> is a {@link UUID} string.
 * The <code>date_of_birth</code> is formatted as a {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}.
 * The <code>creationInstant</code> is formatted as a {@link java.time.format.DateTimeFormatter#ISO_INSTANT}.
 *
 * <p><code>CSVDriverRepository</code> is not thread-safe and requires external synchronization.</p>
 */
public class CSVDriverRepository implements DriverRepository {
    private final Path file;
    private final Charset charset;
    private final Clock clock;
    private final IdGenerator idGenerator;

    public CSVDriverRepository(Path csvFile, Charset charset, Clock clock, IdGenerator idGenerator) {
        this.file = csvFile;
        this.charset = charset;
        this.clock = clock;
        this.idGenerator = idGenerator;
    }

    @Override
    public Drivers findAll() {
        return find(identity());
    }

    @Override
    public Drivers findFrom(Instant earliestDate) {
        return find(driverStream -> driverStream.filter(driver -> !driver.getCreated().isBefore(earliestDate)));
    }

    private Drivers find(Function<Stream<Driver>, Stream<Driver>> finder) {
        try (Stream<String> lines = Files.lines(file, charset)) {
            return new Drivers(finder.apply(lines.map(this::parse)).collect(Collectors.toList()));
        } catch (NoSuchFileException e) {
            return new Drivers();
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    private Driver parse(String line) {
        String[] tokens = line.split(",");
        return new Driver.Builder(tokens[1], tokens[2], LocalDate.parse(tokens[3]))
                .withId(UUID.fromString(tokens[0]))
                .createdAt(Instant.parse(tokens[4]))
                .build();
    }

    @Override
    public Driver save(Driver driver) {
        driver.setId(idGenerator.generateId());
        driver.setCreated(clock.instant());

        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, WRITE, CREATE, APPEND)) {
            writer.write(format(driver));
            writer.newLine();
        } catch (IOException e) {
            throw new RepositoryException(e);
        }

        return driver;
    }

    private String format(Driver driver) {
        StringJoiner csvRecord = new StringJoiner(",");
        csvRecord.add(driver.getId().toString());
        csvRecord.add(driver.getFirstName());
        csvRecord.add(driver.getLastName());
        csvRecord.add(ISO_LOCAL_DATE.format(driver.getDateOfBirth()));
        csvRecord.add(ISO_INSTANT.format(driver.getCreated()));
        return csvRecord.toString();
    }
}
