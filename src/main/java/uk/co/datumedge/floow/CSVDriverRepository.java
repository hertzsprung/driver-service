package uk.co.datumedge.floow;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.*;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * Stores {@link Drivers} in a CSV file.  The CSV file is formatted as:
 * <pre>
 *     firstName,lastName,creationInstant
 *     ...
 * </pre>
 * with no header line and no quoted fields.
 * The <code>creationInstant</code> is formatted as an {@link java.time.format.DateTimeFormatter#ISO_INSTANT}.
 */
public class CSVDriverRepository implements DriverRepository {
    private final Path file;
    private final Charset charset;
    private final Clock clock;

    public CSVDriverRepository(Path csvFile, Charset charset, Clock clock) {
        this.file = csvFile;
        this.charset = charset;
        this.clock = clock;
    }

    @Override
    public Drivers findAll() {
        try (Stream<String> lines = Files.lines(file, charset)) {
            return new Drivers(lines.map(this::parse).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    private Driver parse(String line) {
        String[] tokens = line.split(",");
        return new Driver(tokens[0], tokens[1], Instant.from(ISO_INSTANT.parse(tokens[2])));
    }

    @Override
    public Driver save(Driver driver) {
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
        csvRecord.add(driver.getFirstName());
        csvRecord.add(driver.getLastName());
        csvRecord.add(ISO_INSTANT.format(driver.getCreated()));
        return csvRecord.toString();
    }
}
