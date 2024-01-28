package ftn.isa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/location")
public class LocationController {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private static RestTemplate restTemplate = new RestTemplate();
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public LocationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping(value="/send-message", consumes="text/plain")
    public void sendRequestToOtherApp(@RequestBody String text) {
        // Send the message via WebSocket I WANT THIS MESSAGE TO BE SENT

        String url = "http://localhost:8080/api/myexchange/spring-boot2";
        restTemplate.postForObject(url, text, String.class);
    }

    public void sendMessageToExternalApp(String text) {
        try {
            messagingTemplate.convertAndSend("/socket-publisher", text);
            String url = "http://localhost:8082/api/location/get-message";
            restTemplate.postForObject(url, text, String.class);
        } catch(Exception e) {
            log.error("Error sending message to external app: " + e.getMessage());
        }
    }
}
