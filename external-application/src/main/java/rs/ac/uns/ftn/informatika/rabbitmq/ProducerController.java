package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
}
