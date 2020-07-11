package uk.co.datumedge.floow;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.Instant;
import java.util.Objects;

public class Driver {
    private final String firstName;
    private final String lastName;
    private Instant created;

    @JsonCreator
    public Driver(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Driver(String firstName, String lastName, Instant created) {
        this(firstName, lastName);
        this.created = created;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(firstName, driver.firstName) &&
                Objects.equals(lastName, driver.lastName) &&
                Objects.equals(created, driver.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, created);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", created=" + created +
                '}';
    }
}
