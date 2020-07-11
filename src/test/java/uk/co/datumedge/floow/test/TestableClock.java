package uk.co.datumedge.floow.test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Starts the clock at 1970-01-01T00:00:00Z and increments by one second every time before the clock is read.
 *
 * <p><code>TestableClock</code> is not thread-safe.</p>
 */
public class TestableClock extends Clock {
    private Instant instant;
    private final ZoneId zone;

    public TestableClock() {
        this(EPOCH, UTC);
    }

    private TestableClock(Instant instant, ZoneId zone) {
        this.instant = instant;
        this.zone = zone;
    }

    @Override
    public ZoneId getZone() {
        return UTC;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        if (zone.equals(this.zone)) {
            return this;
        }
        return new TestableClock(instant, zone);
    }

    @Override
    public Instant instant() {
        instant = instant.plus(1, SECONDS);
        return instant;
    }
}
