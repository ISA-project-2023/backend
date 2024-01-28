package rs.ac.uns.ftn.informatika.rabbitmq;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Component
public class Producer {
	
	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	/*
	 * RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
	 * pristup RabbitMQ za slanje i primanje poruka.
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static final String API_KEY = "5b3ce3597851110001cf6248b3e509ec2e52444d8da8beacb1d3f3c9";


	/*
	 * U ovom slucaju routingKey ce biti ime queue.
	 * Poruka se salje u exchange (default exchange u ovom primeru) i
	 * exchange ce rutirati poruke u pravi queue.
	 */
	public void sendTo(String routingkey, String message){
		log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
		this.rabbitTemplate.convertAndSend(routingkey, message);
	}
	/*

	Kljuc: 5b3ce3597851110001cf6248b3e509ec2e52444d8da8beacb1d3f3c9
	 * U ovom slucaju routingKey ce biti ime queue.
	 * Poruka se salje u exchange ciji je naziv prosledjen kao prvi parametar i
	 * exchange ce rutirati poruke u pravi queue.
	 */

}
