package uk.co.datumedge.floow.test.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import uk.co.datumedge.floow.Driver;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class RestSteps {
    private static final String ROOT_URL = "http://localhost:8080";
    private final TestRestTemplate client = new TestRestTemplate();
    private String responseBody;

    @Given("the drivers: $driverDatabase")
    public void postDrivers(ExamplesTable driverDatabase) {
        for (Map<String, String> driver : driverDatabase.getRows()) {
            postDriver(driver);
        }
    }

    private void postDriver(Map<String, String> driverParameters) {
        LocalDate dateOfBirth = LocalDate.parse(driverParameters.get("date_of_birth"));
        Driver driver = new Driver(driverParameters.get("firstname"), driverParameters.get("lastname"), dateOfBirth);
        ResponseEntity<String> response = this.client.postForEntity(ROOT_URL + "/driver/create", driver, String.class);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @When("I GET $path")
    public void get(String path) {
        ResponseEntity<String> responseEntity = this.client.getForEntity(ROOT_URL + path, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        responseBody = responseEntity.getBody();
    }

    @Then("the JSON response body is: $expectedResponse")
    public void assertJSONResponseBody(String expectedResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJson = objectMapper.readTree(expectedResponse);
        JsonNode actualJson = objectMapper.readTree(responseBody);

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
