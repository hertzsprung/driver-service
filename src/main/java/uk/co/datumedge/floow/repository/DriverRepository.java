package uk.co.datumedge.floow.repository;

import uk.co.datumedge.floow.Driver;
import uk.co.datumedge.floow.Drivers;

public interface DriverRepository {
    /**
     * Find all drivers ordered by creation date.
     */
    Drivers findAll();

    Driver save(Driver driver);
}
