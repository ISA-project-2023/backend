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
    public ContractProducer() {
    }
    public void sendTo(String routingkey, String message) {
        log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
        this.rabbitTemplate.convertAndSend(routingkey, message);
    }

    public void send(String routingkey, String message) {
        //log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
        this.rabbitTemplate.convertAndSend(routingkey, message);
    }
}
