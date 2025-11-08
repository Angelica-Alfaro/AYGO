package edu.eci.uber.user.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.uber.user.domain.RideDto;
import edu.eci.uber.user.domain.RideRequest;

@Service
public class RideClient {
	
	private HttpClient client;
	private final String host = "https://ouqzrelm6j.execute-api.us-east-1.amazonaws.com/AYGO";
	
	public RideClient() {
		this.client = HttpClient.newHttpClient();
	}
	
	public List<RideRequest> GetOptionsRide(float[] startPoint,float[] endPoint){
		String uri = String.format("%s/rides/options?startingx=%s&startingy=%s&endingx=%s&endingy=%s",host, startPoint[0],startPoint[1],endPoint[0],endPoint[1]);
		System.out.println(uri);
		HttpRequest request = HttpRequest.newBuilder()
	              .uri(URI.create( uri ))
	              .GET()
	              .build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response);
			if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                List<RideRequest> drivers = mapper.readValue(response.body(), new TypeReference<List<RideRequest>>() {});
                
                return drivers;
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
		} 
		catch (Exception e) {
			System.out.println("Error en la solicitud: " + e);
		}
		
		return null;
	}
	
	public void Create(RideDto rideDto) {
		String uri = String.format("%s/ride",host);
		System.out.println(uri);
		ObjectMapper body = new ObjectMapper();
		try {
        String json = body.writeValueAsString(rideDto);
		HttpRequest request = HttpRequest.newBuilder()
	              .uri(URI.create( uri ))
	              .header("Content-Type", "application/json")
	              .POST(HttpRequest.BodyPublishers.ofString(json))
	              .build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(json);
		System.out.println(response);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
