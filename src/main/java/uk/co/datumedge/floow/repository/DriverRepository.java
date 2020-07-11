package uk.co.datumedge.floow.repository;

import uk.co.datumedge.floow.Driver;
import uk.co.datumedge.floow.Drivers;

import java.time.Instant;

public interface DriverRepository {
    /**
     * Find all drivers ordered by creation date.
     */
    Drivers findAll();

    /**
     * Find all drivers created on or after the specified date, ordered by creation date.
     */
    Drivers findFrom(Instant earliestDate);

    /**
     * Save a driver, setting its creation date and ID.
     *
     * @param driver a <code>Driver</code> instance
     * @return The same <code>Driver</code> instance
     */
    Driver save(Driver driver);
}
