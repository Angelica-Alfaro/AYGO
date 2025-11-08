package edu.eci.uber.ride.domain;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Driver {
	
	private UUID id;
	private String name;
	private float[] location = new float[] {0,0};
	private String vehicle;
	private boolean available = true;
	private int passengers;
	
	public Driver() {}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float[] getLocation() {
		return location;
	}

	public void setLocation(float[] location) {
		this.location = location;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
}
