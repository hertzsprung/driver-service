package uk.co.datumedge.floow.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import uk.co.datumedge.floow.Driver;
import uk.co.datumedge.floow.DriverRepository;
import uk.co.datumedge.floow.test.system.NewDriver;

import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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

        Driver savedDriver = new Driver("Lucas", "Rees");
        verify(repository).save(savedDriver);
    }

    private String rootUrl() {
        return "http://localhost:" + port;
    }
}
