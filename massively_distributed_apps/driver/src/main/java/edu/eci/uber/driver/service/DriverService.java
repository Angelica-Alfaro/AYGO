package edu.eci.uber.driver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.uber.driver.domain.Driver;
import edu.eci.uber.driver.repositories.DriverRepository;

@Service
public class DriverService {

	private final DriverRepository driverRepository;
	
	public DriverService(@Autowired DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}
	
	public Driver create(Driver driver) {
		driver.setId(UUID.randomUUID());
    	driverRepository.save(driver);
    	return GetDriverById(driver.getId());
    }
	
	public Driver GetDriverById(UUID driverId) {
		return driverRepository.findById(driverId).get();
	}
	
	public List<Driver> getAll(){
		return driverRepository.findAll();
	}
	
	public List<Driver> getNearbyDrivers(float[] startingPoint){
		List<Driver> drivers= getAll();
		List<Driver> nearbyDrivers = new ArrayList<Driver>();
		for (int i = 0; i < drivers.size(); i++) 
		{
			if(drivers.get(i).isAvailable() && CalculateDistanceBetweenPoints(startingPoint, drivers.get(i).getLocation()) < 30) {
				nearbyDrivers.add(drivers.get(i));
			}
		} 
		
		return nearbyDrivers;
	}
	
	private double CalculateDistanceBetweenPoints(float[] point1, float[] point2) {
		return Math.sqrt( Math.pow( point2[0] - point1[0],2 ) + Math.pow( point2[1] - point1[1], 2 ));
	}
	
	public Driver UpdateStatus(UUID driverId,boolean status) {
		Driver driver = GetDriverById(driverId);
		driver.setAvailable(status);
		driverRepository.save(driver);
    	return GetDriverById(driverId);
    }

	public Driver updateLocation(UUID driverId, float[] location) {
		Driver driver = GetDriverById(driverId);
		driver.setLocation(location);
		driverRepository.save(driver);
    	return GetDriverById(driverId);
	}

}
