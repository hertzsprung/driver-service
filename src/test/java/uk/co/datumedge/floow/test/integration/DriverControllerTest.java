package uk.co.datumedge.floow.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import uk.co.datumedge.floow.Driver;
import uk.co.datumedge.floow.Drivers;
import uk.co.datumedge.floow.repository.DriverRepository;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static uk.co.datumedge.floow.test.TestDrivers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DriverControllerTest {
    @Autowired
    private TestRestTemplate client;

    @LocalServerPort
    private int port;

    @MockBean
    private DriverRepository repository;

    @Test
    public void savesDriverToRepository() {
        ResponseEntity<String> responseEntity = post(LUCAS_REES.build());
        System.out.println(responseEntity.getBody());

        verify(repository).save(LUCAS_REES.build());
    }

    @Test
    public void failToSaveDriverWithWhitespaceFirstName() {
        Driver noFirstName = new Driver.Builder(" ", "Rees", DATE_OF_BIRTH).build();
        assertBadRequest(post(noFirstName));
    }

    @Test
    public void failToSaveDriverWithWhitespaceLastName() {
        Driver noLastName = new Driver.Builder("Lucas", " ", DATE_OF_BIRTH).build();
        assertBadRequest(post(noLastName));
    }

    @Test
    public void failToSaveDriverWithCommaInFirstName() {
        Driver noFirstName = new Driver.Builder("Lucas,", "Rees", DATE_OF_BIRTH).build();
        assertBadRequest(post(noFirstName));
    }

    @Test
    public void failToSaveDriverWithCommaInLastName() {
        Driver noLastName = new Driver.Builder("Lucas", "Re,es", DATE_OF_BIRTH).build();
        assertBadRequest(post(noLastName));
    }

    @Test
    public void failToSaveDriverWithoutDateOfBirth() {
        Driver noDateOfBirth = new Driver.Builder("Lucas", "Rees", null).build();
        assertBadRequest(post(noDateOfBirth));
    }

    private ResponseEntity<String> post(Driver noFirstName) {
        return this.client.postForEntity(rootUrl() + "/driver/create", noFirstName, String.class);
    }

    private void assertBadRequest(ResponseEntity<String> responseEntity) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void getsAllDriversFromRepository() {
        Drivers expectedDrivers = new Drivers(LUCAS_REES.build(), JESSICA_GREENE.build());
        when(repository.findAll()).thenReturn(expectedDrivers);

        Drivers actualDrivers = this.client.getForObject(rootUrl() + "/drivers", Drivers.class);
        assertThat(actualDrivers).isEqualTo(expectedDrivers);
    }

    @Test
    public void getsDriversByDateFromRepository() {
        Drivers expectedDrivers = new Drivers(JESSICA_GREENE.build());
        when(repository.findFrom(Instant.ofEpochSecond(2))).thenReturn(expectedDrivers);

        Drivers actualDrivers = this.client.getForObject(rootUrl() + "/drivers/byDate?date=1970-01-01T00:00:02Z", Drivers.class);
        assertThat(actualDrivers).isEqualTo(expectedDrivers);
    }

    private String rootUrl() {
        return "http://localhost:" + port;
    }
}
