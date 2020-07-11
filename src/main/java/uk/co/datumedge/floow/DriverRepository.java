package uk.co.datumedge.floow;

public interface DriverRepository {
    /**
     * Find all drivers ordered by creation date.
     */
    Drivers findAll();

    Driver save(Driver driver);
}
