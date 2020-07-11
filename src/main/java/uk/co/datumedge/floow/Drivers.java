package uk.co.datumedge.floow;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drivers {
    private final List<Driver> drivers;

    public Drivers() {
        this(new ArrayList<>());
    }

    @JsonCreator
    public Drivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drivers other = (Drivers) o;
        return Objects.equals(drivers, other.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drivers);
    }

    @Override
    public String toString() {
        return "Drivers{" +
                drivers +
                '}';
    }
}
