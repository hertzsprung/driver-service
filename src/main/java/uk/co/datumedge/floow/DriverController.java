package uk.co.datumedge.floow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.datumedge.floow.repository.DriverRepository;

import java.time.Instant;

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

    @GetMapping("/drivers")
    public Drivers findAll() {
        return driverRepository.findAll();
    }

    @GetMapping("/drivers/byDate")
    public Drivers findFromDate(@RequestParam Instant date) {
        return driverRepository.findFrom(date);
    }
}
