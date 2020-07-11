package uk.co.datumedge.floow;

public class CSVDriverRepository implements DriverRepository {
    @Override
    public Drivers findAll() {
        return new Drivers();
    }

    @Override
    public Driver save(Driver driver) {
        return driver;
    }
}
