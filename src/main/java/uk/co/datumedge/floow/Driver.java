package uk.co.datumedge.floow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Driver {
    private UUID id;
    private final String firstName;
    private final String lastName;
    private Instant created;

    @JsonCreator
    public Driver(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Driver(UUID id, String firstName, String lastName, Instant created) {
        this(firstName, lastName);
        this.id = id;
        this.created = created;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("firstname")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastname")
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
        return Objects.equals(id, driver.id) &&
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(lastName, driver.lastName) &&
                Objects.equals(created, driver.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, created);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", created=" + created +
                '}';
    }

    public static class Builder {
        private final Driver driver;

        public Builder(String firstName, String lastName) {
            this.driver = new Driver(firstName, lastName);
        }

        public Builder createdAt(Instant instant) {
            this.driver.setCreated(instant);
            return this;
        }

        public Builder withId(long id) {
            return this.withId(new UUID(0, id));
        }

        public Builder withId(UUID id) {
            this.driver.setId(id);
            return this;
        }

        public Driver build() {
            return driver;
        }
    }
}
