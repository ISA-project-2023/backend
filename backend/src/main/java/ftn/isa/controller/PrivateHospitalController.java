package ftn.isa.controller;

import ftn.isa.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ftn.isa.dto.ContractDTO;

import java.time.LocalDateTime;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/private-hospital")
public class PrivateHospitalController {
    private static final Logger log = LoggerFactory.getLogger(PrivateHospitalConsumer.class);
    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public PrivateHospitalController(RestTemplate rt) { restTemplate = rt; }

    @Autowired
    private ContractProducer producer;

    // NEW CONTRACT
    @PostMapping(value="/new-contract/{queue}", consumes="text/plain")
    public void newContract(@PathVariable("queue") String queue, @RequestBody String text) {
        if (!text.isEmpty()){
            producer.send(queue, text);
        }
    }
    public static ContractDTO deserializeMessageToContractDTO(String message){
        String[] parts = message.split(",");
        // check if there are right number of string in parts
        if (parts.length != 6){
            return null;
        }
        // message example:
        // Hospital1,Address1,Lekovi za ujed komarca,5,Medicine Solution,2024-02-15T12:00:00
        ContractDTO contractDTO = new ContractDTO(null, parts[0], parts[1], parts[2], parts[4], LocalDateTime.parse(parts[5]), true, Integer.parseInt(parts[3]));
        return contractDTO;
    }

    // CANCEL CONTRACT
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