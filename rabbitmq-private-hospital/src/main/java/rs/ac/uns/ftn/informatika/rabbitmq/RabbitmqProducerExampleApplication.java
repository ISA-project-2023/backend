package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

/* Za pokretanje primera potrebno je instalirati RabbitMQ - https://www.rabbitmq.com/download.html */
@SpringBootApplication
public class RabbitmqProducerExampleApplication {
	@Value("${myqueue3}")
	String queue3;
	@Value("${myqueue4}")
	String queue4;
	@Value("${myqueue5}")
	String queue5;

	@Value("${myexchange2}")
	String exchange;

	@Bean
	Queue queue3() { return new Queue(queue3, true); }
	@Bean
	Queue queue4() { return new Queue(queue4, true); }
	@Bean
	Queue queue5() { return new Queue(queue5, true); }
	@Bean
	DirectExchange exchange() { return new DirectExchange(exchange); }

//	@Bean
//	Binding binding(Queue queue3, DirectExchange exchange) { return BindingBuilder.bind(queue3).to(exchange).with("spring-boot3"); }
	@Bean
	Binding binding3(Queue queue3, DirectExchange exchange) { return BindingBuilder.bind(queue3).to(exchange).with("spring-boot3"); }
	@Bean
	Binding binding4(Queue queue4, DirectExchange exchange) { return BindingBuilder.bind(queue4).to(exchange).with("spring-boot4"); }

	/* Registrujemo bean koji ce sluziti za konekciju na RabbitMQ gde se mi u
	 * primeru kacimo u lokalu.		*/
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqProducerExampleApplication.class, args);
		runInput();
	}

	@Autowired
	private Producer producer;
	private static void runInput() {
		ProducerController.inputNewContract(true);
		// TODO -> fix this
		// producer.sendTo(routingKey, contract);
	}

//	@Bean
//	public void run() {
//		String routingKey = "spring-boot3";
//		while (true) {
//			System.out.println("Create a new contract:");
//			System.out.println("Equipment:   Amount:   Company:   Date(yyyy-mm-ddThh:MM:ss):   ");
//			Scanner scanner = new Scanner(System.in);
//			String contract = scanner.nextLine();
//			contract = "Hospital1,Address1," + contract;
//			producer.sendTo(routingKey, contract);
//		}
//	}

	// TODO - monthly delivery - Schedule
}
