package ftn.isa.controller;

import ftn.isa.domain.LocationConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.logging.ErrorManager;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/location")
public class LocationController {

    private static final Logger log = LoggerFactory.getLogger(LocationConsumer.class);
    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public LocationController(RestTemplate rt) {
        restTemplate = rt;
    }

    @PostMapping(value="/send-message", consumes="text/plain")
    public void sendRequestToOtherApp(@RequestBody String text) {
        String url = "http://localhost:8080/api/myexchange/spring-boot2";
        restTemplate.postForObject(url, text, String.class);
    }

    public static void sendMessageToExternalApp(String text) {
        try {
            String url = "http://localhost:8082/api/location/get-message";
            restTemplate.postForObject(url, text, String.class);
        } catch(Exception e) {
            log.error("Error sending message to external app: " + e.getMessage());
        }
    }

}
