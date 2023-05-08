package architectures.rest.java.java;

import org.springframework.web.client.RestTemplate;

import architectures.rest.server.Greeting;

/**
 * This client uses the RestTemplate class to send a GET request to the
 * /greeting endpoint with the name query parameter set to John. It then maps
 * the response JSON to a Greeting object using the getForObject method, and
 * prints the content of the greeting to the console.
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

public class GreetingClient {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Greeting greeting = restTemplate.getForObject("http://localhost:8080/greeting?name=John", Greeting.class);
        System.out.println(greeting.getContent());
    }
}
