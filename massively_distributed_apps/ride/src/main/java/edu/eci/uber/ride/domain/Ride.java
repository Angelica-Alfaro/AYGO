package edu.eci.uber.ride.domain;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ride {
	
	private float[] startingPoint;
	private float[] endingPoint;
	private double price;
	private RideStatus rideStatus;
	private UUID userId;
	private UUID driverId;
	private UUID id;
	
	public Ride(float[] startingPoint, float[] endingPoint, double price, UUID userId, UUID driverId) {
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.price = price;
		this.userId = userId;
		this.driverId = driverId;
		rideStatus = RideStatus.Traveling;
	}


	public float[] getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(float[] startingPoint) {
		this.startingPoint = startingPoint;
	}
	public float[] getEndingPoint() {
		return endingPoint;
	}
	public void setEndingPoint(float[] endingPoint) {
		this.endingPoint = endingPoint;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public RideStatus getRideStatus() {
		return rideStatus;
	}
	public void setRideStatus(RideStatus rideStatus) {
		this.rideStatus = rideStatus;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public UUID getDriverId() {
		return driverId;
	}
	public void setDriverId(UUID driverId) {
		this.driverId = driverId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
