package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static rs.ac.uns.ftn.informatika.rabbitmq.RabbitmqProducerExampleApplication.generateRequest;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping(value = "api/location")
public class ProducerController {

	private static RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public ProducerController(RestTemplate rt) {
		restTemplate = rt;
	}

	@PostMapping(value="/get-message", consumes="text/plain")
	public void getMessage(@RequestBody String text) {
		System.out.println("Final message: " + text);
	}
	public static void sendRequestToOtherApp(String text) {
		String url = "http://localhost:8084/api/location/send-message";
		restTemplate.postForObject(url, text, String.class);
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/start-message-sending")
	public ResponseEntity<String> startMessageSending() {
		// Start the message sending process
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> generateRequest(), 0, 3, TimeUnit.SECONDS);

		return ResponseEntity.ok("Message sending started successfully.");
	}
}
