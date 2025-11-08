package edu.eci.uber.ride.domain;

import java.util.UUID;

public class RideRequest {
	
	private float[] startingPoint;
	private float[] endingPoint;
	private float[] positionDriver;
	private double price;
	private double distance;
	private UUID driverId;
	
	public RideRequest() {}

	public RideRequest(float[] startingPoint, float[] endingPoint, double price, double distance, float[] positionDriver,UUID driverId) {
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.price = price;
		this.distance = distance;
		this.positionDriver = positionDriver;
		this.driverId = driverId;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float[] getPositionDriver() {
		return positionDriver;
	}

	public void setPositionDriver(float[] positionDriver) {
		this.positionDriver = positionDriver;
	}

	public UUID getDriverId() {
		return driverId;
	}

	public void setDriverId(UUID driverId) {
		this.driverId = driverId;
	}
}
