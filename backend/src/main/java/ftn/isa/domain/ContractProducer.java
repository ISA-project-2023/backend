package ftn.isa.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractProducer {
    private static final Logger log = LoggerFactory.getLogger(ContractProducer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final String API_KEY = "5b3ce3597851110001cf6248b3e509ec2e52444d8da8beacb1d3f3c9";

    public ContractProducer() {
    }
    public void sendTo(String routingkey, String message) {
        log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
        this.rabbitTemplate.convertAndSend(routingkey, message);
    }
}
