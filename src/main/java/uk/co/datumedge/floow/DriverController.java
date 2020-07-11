package uk.co.datumedge.floow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class DriverController {
    @Autowired
    private DriverRepository driverRepository;

    @PostMapping("/driver/create")
    public ResponseEntity<Driver> create(@RequestBody Driver driver) {
        driverRepository.save(driver);
        return new ResponseEntity<>(driver, CREATED);
    }
}
