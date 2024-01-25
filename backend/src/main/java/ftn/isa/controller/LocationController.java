package ftn.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/location")
public class LocationController {

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public LocationController(RestTemplate rt) {
        restTemplate = rt;
    }

    @PostMapping(value="/send-message", consumes="text/plain")
    public String sendRequestToOtherApp(@RequestBody String text) {
        String url = "http://localhost:8080/api/spring-boot1";
        String response = restTemplate.postForObject(url, text, String.class);

        return "Response from other app: " + response;
    }

    public static void sendMessageToExternalApp(String text) {
        String url = "http://localhost:8082/api/location/get-message";
        restTemplate.postForObject(url, text, String.class);
    }
}
