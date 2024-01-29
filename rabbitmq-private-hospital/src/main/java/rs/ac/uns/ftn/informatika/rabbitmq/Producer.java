package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class Producer {
	
	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	/* RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
	 * pristup RabbitMQ za slanje i primanje poruka.	*/
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	/* U ovom slucaju routingKey ce biti ime queue.
	 * Poruka se salje u exchange (default exchange u ovom primeru) i
	 * exchange ce rutirati poruke u pravi queue.	*/
	public void sendTo(String routingkey, String message){
		log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
		this.rabbitTemplate.convertAndSend(routingkey, message);
	}
}
