package uk.co.datumedge.floow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HelloWorldTest {

    @Autowired
    private TestRestTemplate client;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() {
    }

    @Test
    public void contentIsHelloWorld() {
        HelloWorld response = this.client.getForObject("http://localhost:" + port + "/hello", HelloWorld.class);
        assertThat(response.getContent()).isEqualTo("Hello world");
    }
}
