package edu.eci.uber.ride.domain;

import java.util.UUID;

public class RideDto {
	
	private float[] startingPoint;
	private float[] endingPoint;
	private UUID userId;
	private UUID driverId;
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
}
