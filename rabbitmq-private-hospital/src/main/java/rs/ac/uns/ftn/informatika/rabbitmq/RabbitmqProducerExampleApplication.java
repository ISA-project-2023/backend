package rs.ac.uns.ftn.informatika.rabbitmq;

import org.json.JSONException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.ComponentScan;

/*
 * 
 * Za pokretanje primera potrebno je instalirati RabbitMQ - https://www.rabbitmq.com/download.html
 */
@SpringBootApplication
public class RabbitmqProducerExampleApplication {
	@Value("spring-boot3")
	String queue;
	@Value("myexchange2")
	String exchange;
	@Bean
	Queue queue() {
		return new Queue(queue, true);
	}
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
	@Autowired
	private Producer producer;
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqProducerExampleApplication.class, args);
	}



	@Bean
	Binding binding(Queue queue2, DirectExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with(queue);
	}
	/*
	 * Registrujemo bean koji ce sluziti za konekciju na RabbitMQ gde se mi u
	 * primeru kacimo u lokalu.
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}

	@Bean
	public void run() {
		String routingKey = "spring-boot3";
		while (true) {
			System.out.println("Create a new contract:");
			System.out.println("Equipment:   Amount:   Company:   Date(yyyy-mm-ddThh:MM:ss):   ");
			Scanner scanner = new Scanner(System.in);
			String contract = scanner.nextLine();
			StringBuilder builder = new StringBuilder();
			builder.append("Hospital1,Address1,").append(contract);
			contract = builder.toString();
			producer.sendTo(routingKey, contract);
		}
	}

}
