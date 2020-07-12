package uk.co.datumedge.floow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Driver {
    private UUID id;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private Instant created;

    @JsonCreator
    public Driver(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Driver(UUID id, String firstName, String lastName, LocalDate dateOfBirth, Instant created) {
        this(firstName, lastName, dateOfBirth);
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
    @NotBlank(message="firstname cannot be blank")
    @Pattern(regexp="^((?!,).)*$", message = "firstname cannot contain ','") // https://stackoverflow.com/a/406408
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastname")
    @NotBlank(message="lastname cannot be blank")
    @Pattern(regexp="^((?!,).)*$", message = "lastname cannot contain ','")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("date_of_birth")
    @NotNull
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
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
                Objects.equals(dateOfBirth, driver.dateOfBirth) &&
                Objects.equals(created, driver.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth, created);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", created=" + created +
                '}';
    }

    public static class Builder {
        private final Driver driver;

        public Builder(String firstName, String lastName, LocalDate dateOfBirth) {
            this.driver = new Driver(firstName, lastName, dateOfBirth);
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
