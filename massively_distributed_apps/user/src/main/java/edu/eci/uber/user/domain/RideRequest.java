package edu.eci.uber.user.domain;

public class RideRequest {
	
	private float[] startingPoint;
	private float[] endingPoint;
	private float[] positionDriver;
	private double price;
	private double distance;
	private String driverId;
	
	public RideRequest() {}

	public RideRequest(float[] startingPoint, float[] endingPoint, double price, double distance, float[] positionDriver) {
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.price = price;
		this.distance = distance;
		this.positionDriver = positionDriver;
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

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
}
