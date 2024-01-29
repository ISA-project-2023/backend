package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@RestController
@CrossOrigin(origins = "http://localhost:8085")
@RequestMapping(value = "api")
public class ProducerController {

	private static RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public ProducerController(RestTemplate rt) {
		restTemplate = rt;
	}

	@Autowired
	private Producer producer;

	// NEW CONTRACT
	public static void inputNewContract(boolean canInput) {
		String url = "http://localhost:8084/api/private-hospital/new-contract/spring-boot3";
		while (canInput) {
			System.out.println("Create a new contract:");
			System.out.println("Equipment:   Amount:   Company:   Date(yyyy-mm-ddThh:MM:ss):   ");
			Scanner scanner = new Scanner(System.in);
			String contract = scanner.nextLine();
			if (!contract.isEmpty()){
				contract = "Hospital1,Address1," + contract;
				restTemplate.postForObject(url, contract, String.class);
			}
			//return contract;
		}
		//return "";
	}

	// CANCEL CONTRACT
	@PostMapping(value="/cancel/{queue}", consumes = "text/plain")
	public ResponseEntity<String> deliveryCanceled(@PathVariable("queue") String queue, @RequestBody String message) {
		System.out.println("\nCancellation message from other app: \t" + message + "\n");
		return ResponseEntity.ok().build();
	}

	// DELIVERY
	@PostMapping(value="/deliver/{queue}", consumes = "text/plain")
	public ResponseEntity<String> deliveryStarted(@PathVariable("queue") String queue, @RequestBody String message) {
		System.out.println(message);
		return ResponseEntity.ok().build();
	}
}