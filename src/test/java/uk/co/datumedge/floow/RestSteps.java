package uk.co.datumedge.floow;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

public class RestSteps {
    private static final String ROOT_URL = "http://localhost:8080";
    private final TestRestTemplate client = new TestRestTemplate();
    private String response;

    @Given("the drivers: $driverDatabase")
    public void postDrivers(ExamplesTable driverDatabase) {
        for (Map<String, String> driver : driverDatabase.getRows()) {
            postDriver(driver);
        }
    }

    private void postDriver(Map<String, String> driverParameters) {
        NewDriver newDriver = new NewDriver(driverParameters.get("firstName"), driverParameters.get("lastName"));
        ResponseEntity<String> response = this.client.postForEntity(ROOT_URL + "/driver/create", newDriver, String.class);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @When("I GET $path")
    public void get(String path) {
        response = this.client.getForObject(ROOT_URL + path, String.class);
    }

    @Then("the JSON response is: $expectedResponse")
    public void assertJSONResponse(String expectedResponse) {
        assertThat(response).isEqualTo(expectedResponse);
    }
}
