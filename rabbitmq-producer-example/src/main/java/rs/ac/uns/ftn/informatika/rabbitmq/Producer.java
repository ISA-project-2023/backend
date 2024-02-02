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

	public static String getRoute(String startPoint, String endPoint) throws IOException {
		String routeUrl = "https://api.openrouteservice.org/v2/directions/driving-car" +
				"?api_key=" + API_KEY +
				"&start=" + URLEncoder.encode(startPoint, "UTF-8") +
				"&end=" + URLEncoder.encode(endPoint, "UTF-8");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(routeUrl);
		CloseableHttpResponse response = httpClient.execute(request);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		}
	}
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
	public void sendToExchange(String exchange, String routingkey, String message) throws InterruptedException, IOException, JSONException {
		String startPoint = "20.412597,44.807173";
		String endPoint = "19.846801,45.286481";

		String route = Producer.getRoute(startPoint, endPoint);
		List<double[]> coordinates = parseCoordinatesFromRoute(route, exchange, routingkey);

		for(int i = 0; i < coordinates.size(); ++i) {
			double[] coordinate = coordinates.get(i);
			double latitude = coordinate[0];
			double longitude = coordinate[1];

			String location = latitude + "," + longitude;
			log.info("Sending> ... Message=[ " + location + " ] Exchange=[" + exchange + "] RoutingKey=[" + routingkey + "]");
			this.rabbitTemplate.convertAndSend(exchange, routingkey, location);

			Thread.sleep(4000);
		}
		this.rabbitTemplate.convertAndSend(exchange, routingkey, message);
	}

	private List<double[]> parseCoordinatesFromRoute(String route, String exchange, String routingkey) throws JSONException {
		JSONObject routeJson = new JSONObject(route);
		JSONArray coordinates = routeJson.getJSONArray("features").getJSONObject(0)
				.getJSONObject("geometry").getJSONArray("coordinates");

		JSONArray features = routeJson.getJSONArray("features");
		JSONObject summary = features.getJSONObject(0).getJSONObject("properties").getJSONObject("summary");
		double duration = summary.getDouble("duration");

		System.out.println("Trajanje: " + duration);
		this.rabbitTemplate.convertAndSend(exchange, "spring-boot1", Double.toString(duration));
		log.info("Sending> ... Message=[ " + duration + " ] Exchange=[" + exchange + "] RoutingKey=[" + "spring-boot1" + "]");
		System.out.println(coordinates);

		List<double[]> result = new ArrayList<>();
		for (int i = 0; i < coordinates.length(); i++) {
			JSONArray coordinate = coordinates.getJSONArray(i);
			double longitude = coordinate.getDouble(0);
			double latitude = coordinate.getDouble(1);

			result.add(new double[]{latitude, longitude});
		}

		return result;
	}
}
