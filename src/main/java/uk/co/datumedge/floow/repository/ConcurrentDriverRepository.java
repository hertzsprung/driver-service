package uk.co.datumedge.floow.repository;

import uk.co.datumedge.floow.model.Driver;
import uk.co.datumedge.floow.model.Drivers;

import java.time.Instant;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;

/**
 * Ensures safe concurrent access to an underlying <code>DriverRepository</code> implementation.
 */
public class ConcurrentDriverRepository implements DriverRepository {
    private final DriverRepository delegate;
    private final StampedLock lock = new StampedLock();

    public ConcurrentDriverRepository(DriverRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Drivers findAll() {
        return withReadLock(delegate::findAll);
    }

    @Override
    public Drivers findFrom(Instant earliestDate) {
        return withReadLock(() -> delegate.findFrom(earliestDate));
    }

    private Drivers withReadLock(Supplier<Drivers> supplier) {
        long stamp = lock.readLock();
        try {
            return supplier.get();
        } finally {
            lock.unlockRead(stamp);
        }
    }

    @Override
    public Driver save(Driver driver) {
        long stamp = lock.writeLock();
        try {
            return delegate.save(driver);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}
