package edu.eci.uber.ride.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.uber.ride.clients.DriverClient;
import edu.eci.uber.ride.domain.Driver;
import edu.eci.uber.ride.domain.Ride;
import edu.eci.uber.ride.domain.RideRequest;
import edu.eci.uber.ride.domain.RideStatus;
import edu.eci.uber.ride.repository.RideRepository;

@Service
public class RideService {
	
	private DriverClient driverClient;
	private RideRepository rideRepository;
	private final float pricePerKilometer = 1000;
	
	public RideService(@Autowired DriverClient driverClient, @Autowired RideRepository rideRepository) {
		this.driverClient = driverClient;
		this.rideRepository = rideRepository;
	}
	
	public List<Ride> getAll(){
		return rideRepository.findAll();
	}
	
	public void Delete(UUID riderId){
		rideRepository.deleteById(riderId);
	}
	
	public Ride GetRiderById(UUID riderId) {
		return rideRepository.findById(riderId).get();
	}
	
	private Ride create(Ride ride) {
		ride.setId(UUID.randomUUID());
		rideRepository.save(ride);
    	return GetRiderById(ride.getId());
    }
	
	public Ride Create(UUID userId, UUID driverId,float[] startingPoint, float[] endingPoint) {
		double travelDistance = CalculateDistanceBetweenPoints(startingPoint,endingPoint);
		double priceTravel = (pricePerKilometer) * travelDistance;
		Ride ride = create(new Ride(startingPoint,endingPoint,priceTravel,userId,driverId));
		driverClient.UdpateStatusDriver(driverId, false);
		return ride;
	}

	public List<RideRequest> GetOptionsRides(float[] startingPoint, float[] endingPoint) {
		List<Driver> nearbyDrivers = driverClient.GetNearbyDriver(startingPoint);
		List<RideRequest> rides = new ArrayList<RideRequest>();
		for(int i=0; i< nearbyDrivers.size();i++) {
			double travelDistance = CalculateDistanceBetweenPoints(startingPoint,endingPoint);
			double priceTravel = (pricePerKilometer) * travelDistance;
			
			rides.add(new RideRequest(startingPoint, endingPoint, priceTravel , travelDistance, nearbyDrivers.get(i).getLocation(), nearbyDrivers.get(i).getId()));
		}
		return rides;
	}
	
	private double CalculateDistanceBetweenPoints(float[] point1, float[] point2) {
		
		return Math.sqrt( Math.pow(point2[0] - point1[0],2 ) + Math.pow(point2[1] - point1[1],2 ));
	}

	public Ride finish(UUID rideId) {
		Ride ride = GetRiderById(rideId);
		ride.setRideStatus(RideStatus.Finish);
		driverClient.UdpateStatusDriver(ride.getDriverId(), true);
		Driver driver = new Driver();
		driver.setLocation(ride.getEndingPoint());
		driverClient.UdpateLocationDriver(ride.getDriverId(), driver);
		rideRepository.save(ride);
		return ride;
	}
}
