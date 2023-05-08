package architectures.microservices.example_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
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

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

@RestController
class ApiController {

    private static final String BOOK_CATALOG_SERVICE = "http://localhost:3001";
    private static final String BOOK_INVENTORY_SERVICE = "http://localhost:3002";
    private static final String BOOK_ORDER_SERVICE = "http://localhost:3003";

    private ResponseEntity<String> proxyRequest(HttpServletRequest request, String serviceUrl) {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String url = serviceUrl + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("Host")) {
                headers.set(headerName, request.getHeader(headerName));
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, method, httpEntity, String.class);
    }

    @RequestMapping(value = "/catalog/{path:.+}", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE })
    public ResponseEntity<String> catalogService(HttpServletRequest request) {
        return proxyRequest(request, BOOK_CATALOG_SERVICE);
    }

    @RequestMapping(value = "/inventory/{path:.+}", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE })
    public ResponseEntity<String> inventoryService(HttpServletRequest request) {
        return proxyRequest(request, BOOK_INVENTORY_SERVICE);
    }

    @RequestMapping(value = "/orders/{path:.+}", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE })
    public ResponseEntity<String> ordersService(HttpServletRequest request) {
        return proxyRequest(request, BOOK_ORDER_SERVICE);
    }
}
