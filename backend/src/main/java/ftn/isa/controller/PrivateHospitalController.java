package ftn.isa.controller;

import ftn.isa.domain.PrivateHospitalConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/private-hospital")
public class PrivateHospitalController {
    private static final Logger log = LoggerFactory.getLogger(PrivateHospitalConsumer.class);
    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public PrivateHospitalController(RestTemplate rt) { restTemplate = rt; }

    // CANCEL CONTRACT
//    @PostMapping(value="/cancel", consumes="text/plain")
//    public void cancelContractMessage(@RequestBody String text) {
//        String url = "http://localhost:8085/api/cancel/spring-boot4";
//        restTemplate.postForObject(url, text, String.class);
//    }
    public static void sendCancellationMessage(String text) {
        try {
            String url = "http://localhost:8085/api/cancel/spring-boot4";
            restTemplate.postForObject(url, text, String.class);
        } catch(Exception e) {
            log.error("Error sending message to external app: " + e.getMessage());
        }
    }

    // DELIVERY
    // TODO - monthly deliver
//    @PostMapping(value="/deliver", consumes="text/plain")
//    public void deliverContractMessage(@RequestBody String text) {
//        String url = "http://localhost:8085/api/deliver/spring-boot5";
//        restTemplate.postForObject(url, text, String.class);
//    }

//    public static void sendDeliveryMessage(String text) {
//        try {
//            String url = "http://localhost:8085/api/cancel/spring-boot3";
//            restTemplate.postForObject(url, text, String.class);
//        } catch(Exception e) {
//            log.error("Error sending message to external app: " + e.getMessage());
//        }
//    }
}