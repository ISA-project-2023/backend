package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Producer {
	
	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	/* RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
	 * pristup RabbitMQ za slanje i primanje poruka.	*/
	@Autowired
	private RabbitTemplate rabbitTemplate;


	/* U ovom slucaju routingKey ce biti ime queue.
	 * Poruka se salje u exchange (default exchange u ovom primeru) i
	 * exchange ce rutirati poruke u pravi queue.	*/
	public void sendTo(String routingkey, String message){
		log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
		this.rabbitTemplate.convertAndSend(routingkey, message);
	}

	/*		Kljuc: 5b3ce3597851110001cf6248b3e509ec2e52444d8da8beacb1d3f3c9
	 * U ovom slucaju routingKey ce biti ime queue.
	 * Poruka se salje u exchange ciji je naziv prosledjen kao prvi parametar i
	 * exchange ce rutirati poruke u pravi queue.
	 */

//	private static final String CANCELLATION_ROUTING_KEY = "spring-boot4";
//
//	public void sendCancellationMessage(String message) {
//		log.info("Sending Cancellation> ... Message=[ " + message + " ] RoutingKey=[" + CANCELLATION_ROUTING_KEY + "]");
//		this.rabbitTemplate.convertAndSend(CANCELLATION_ROUTING_KEY, message);
//	}

}
