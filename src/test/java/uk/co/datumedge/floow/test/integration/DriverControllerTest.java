package uk.co.datumedge.floow.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import uk.co.datumedge.floow.DriverRepository;
import uk.co.datumedge.floow.Drivers;
import uk.co.datumedge.floow.test.system.NewDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static uk.co.datumedge.floow.test.TestDrivers.JESSICA_GREENE;
import static uk.co.datumedge.floow.test.TestDrivers.LUCAS_REES;

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
        NewDriver newDriver = new NewDriver("Lucas", "Rees");
        this.client.postForEntity(rootUrl() + "/driver/create", newDriver, String.class);

        verify(repository).save(LUCAS_REES.build());
    }

    @Test
    public void getsAllDriversFromRepository() {
        Drivers expectedDrivers = new Drivers(LUCAS_REES.build(), JESSICA_GREENE.build());
        when(repository.findAll()).thenReturn(expectedDrivers);

        Drivers actualDrivers = this.client.getForObject(rootUrl() + "/drivers", Drivers.class);
        assertThat(actualDrivers).isEqualTo(expectedDrivers);
    }

    private String rootUrl() {
        return "http://localhost:" + port;
    }
}
