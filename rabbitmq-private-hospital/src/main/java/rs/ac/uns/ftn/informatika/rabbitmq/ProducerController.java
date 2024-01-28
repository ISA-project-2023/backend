package rs.ac.uns.ftn.informatika.rabbitmq;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "api")
public class ProducerController {
	
	@Autowired
	private Producer producer;
	
	@PostMapping(value="/deliver/{queue}", consumes = "text/plain")
	public ResponseEntity<String> deliveryStarted(@PathVariable("queue") String queue, @RequestBody String message) {
		System.out.println(message);
		return ResponseEntity.ok().build();
	}
	@PostMapping(value="/cancel/{queue}", consumes = "text/plain")
	public ResponseEntity<String> deliveryCanceled(@PathVariable("queue") String queue, @RequestBody String message) {
		System.out.println(message);
		return ResponseEntity.ok().build();
	}

}
