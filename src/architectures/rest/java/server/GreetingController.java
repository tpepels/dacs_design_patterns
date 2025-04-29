package architectures.rest.java.server;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is annotated with @RestController, which makes it a RESTful
 * controller. It has a method called greeting that handles HTTP GET requests to
 * the /greeting endpoint. It takes a query parameter called name and returns a
 * Greeting object with a unique id and a formatted content string.
 * 
 *
 * Make sure to include the necessary dependencies in your pom.xml file for the
 * Spring Boot and Spring Web projects:
 * 
 * 
 * <dependencies>
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-web</artifactId>
 * </dependency>
 * </dependencies>
 * 
 */

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
