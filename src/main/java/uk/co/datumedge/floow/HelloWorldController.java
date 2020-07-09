package uk.co.datumedge.floow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	@GetMapping("/hello")
	public HelloWorld hello() {
		return new HelloWorld("Hello world");
	}
}