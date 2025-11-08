package edu.eci.uber.ride.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.eci.uber.ride.domain.Driver;

@Service
public class DriverClient {
	
	private HttpClient client;
	private final String host = "https://eyqefwqrpb.execute-api.us-east-1.amazonaws.com/Labs";
	
	public DriverClient() {
		this.client = HttpClient.newHttpClient();
	}
	
	public List<Driver> GetNearbyDriver(float[] point){
		String uri = String.format("%s/drivers/point/%s/%s",host, point[0],point[1]);
		System.out.println(uri);
		HttpRequest request = HttpRequest.newBuilder()
	              .uri(URI.create( uri ))
	              .GET()
	              .build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                List<Driver> drivers = mapper.readValue(response.body(), new TypeReference<List<Driver>>() {});
                
                return drivers;
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
	
	public Driver GetDriverById(UUID driverId){
		String uri = String.format("%s/driver/%s",host, driverId);
		System.out.println(uri);
		HttpRequest request = HttpRequest.newBuilder()
	              .uri(URI.create( uri ))
	              .GET()
	              .build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Driver driver = mapper.readValue(response.body(), new TypeReference<Driver>() {});
                
                return driver;
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
	
	public Driver UdpateStatusDriver(UUID driverId,boolean available){
		String uri = String.format("%s/driver/%s/udpate/status",host, driverId);
		System.out.println(uri);
		HttpRequest request = HttpRequest.newBuilder()
	              .uri(URI.create( uri ))
	              .header("Content-Type", "application/json")
	              .PUT(HttpRequest.BodyPublishers.ofString(available + ""))
	              .build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Driver driver = mapper.readValue(response.body(), new TypeReference<Driver>() {});
                
                return driver;
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
	
	public Driver UdpateLocationDriver(UUID driverId,Driver driverdto){
		String uri = String.format("%s/driver/%s/update/location",host, driverId);
		ObjectMapper body = new ObjectMapper();
		try {
			String json = body.writeValueAsString(driverdto);
			HttpRequest request = HttpRequest.newBuilder()
		              .uri(URI.create( uri ))
		              .header("Content-Type", "application/json")
		              .PUT(HttpRequest.BodyPublishers.ofString(json))
		              .build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Driver driver = mapper.readValue(response.body(), new TypeReference<Driver>() {});
                
                return driver;
            } else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
}
