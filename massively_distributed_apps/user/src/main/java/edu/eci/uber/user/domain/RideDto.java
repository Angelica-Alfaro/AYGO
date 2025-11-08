package edu.eci.uber.user.domain;

import java.util.UUID;

public class RideDto {
	
	private float[] startingPoint;
	private float[] endingPoint;
	private UUID userId;
	private UUID driverId;
	
	public RideDto() {}
	
	
	
	public RideDto(float[] startingPoint, float[] endingPoint, UUID userId, UUID driverId) {
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.userId = userId;
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
