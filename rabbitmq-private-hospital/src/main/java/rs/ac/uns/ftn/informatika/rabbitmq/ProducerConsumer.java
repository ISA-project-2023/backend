package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProducerConsumer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "${myqueue4}", durable = "true"),
                    exchange = @Exchange(value = "${myexchange2}")))
    public void handler(String message) {
        log.info("Consumer> " + message);
        System.out.println("\nMessage from other app: \t" + message);
        System.out.println("Create a new contract:");
        System.out.println("Equipment:   Amount:   Company:   Date(yyyy-mm-ddThh:MM:ss):   ");
    }
}