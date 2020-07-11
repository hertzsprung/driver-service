package uk.co.datumedge.floow;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.*;

/**
 * Stores {@link Drivers} in a CSV file.  The CSV file is formatted as:
 * <pre>
 *     firstName,lastName
 *     ...
 * </pre>
 * with no header line.
 */
public class CSVDriverRepository implements DriverRepository {
    private final Path file;
    private final Charset charset;

    public CSVDriverRepository(Path csvFile, Charset charset) {
        this.file = csvFile;
        this.charset = charset;
    }

    @Override
    public Drivers findAll() {
        try (Stream<String> lines = Files.lines(file, charset)) {
            return new Drivers(lines.map(this::parse).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Driver save(Driver driver) {
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, WRITE, CREATE, APPEND)) {
            writer.write(format(driver));
            writer.newLine();
        } catch (IOException e) {
            throw new RepositoryException(e);
        }

        return driver;
    }

    private Driver parse(String line) {
        String[] tokens = line.split(",");
        return new Driver(tokens[0], tokens[1]);
    }

    private String format(Driver driver) {
        return String.join(",", driver.getFirstName(), driver.getLastName());
    }
}
