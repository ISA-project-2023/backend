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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class RabbitmqProducerExampleApplication {
	@Value("${myqueue3}")
	String queue3;
	@Value("${myqueue4}")
	String queue4;

	@Value("${myexchange2}")
	String exchange;

	@Bean
	Queue queue3() { return new Queue(queue3, true); }
	@Bean
	Queue queue4() { return new Queue(queue4, true); }
	@Bean
	DirectExchange exchange() { return new DirectExchange(exchange); }

	@Bean
	Binding binding(Queue queue3, DirectExchange exchange) { return BindingBuilder.bind(queue3).to(exchange).with("spring-boot3"); }

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqProducerExampleApplication.class, args);
	}

	@Autowired
	private Producer producer;
	@Bean
	public void run() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(() -> {
			while (true) {
				System.out.println("Create a new contract:");
				System.out.println("Equipment:   Amount:   Company:   Date(yyyy-mm-ddThh:MM:ss):   ");
				Scanner scanner = new Scanner(System.in);
				String contract = scanner.nextLine();
				if (!contract.isEmpty()){
					contract = "Hospital1,Address1," + contract;
					producer.sendTo("spring-boot3", contract);
				}
			}
		});
	}
}
