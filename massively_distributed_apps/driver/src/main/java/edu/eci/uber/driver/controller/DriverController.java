package edu.eci.uber.driver.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.uber.driver.domain.Driver;
import edu.eci.uber.driver.service.DriverService;

@RestController
public class DriverController {
	
	@Autowired
    private DriverService driverService;
	
	@GetMapping()
    public String test() {
    	
        return "Test";
    }
	
    @GetMapping("/drivers")
    public List<Driver> getAll() {
    	
        return driverService.getAll();
    }
    
    @GetMapping("/drivers/point/{startingx}/{startingy}")
    public List<Driver> getNearbyDrivers(@PathVariable float startingx, @PathVariable float startingy) {
    	try {
    		float[] point = new float[] {startingx, startingy};
            return driverService.getNearbyDrivers(point);
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return null;
    }
    
    @GetMapping("/driver/{driverId}")
    public Driver getDriverById(@PathVariable UUID driverId) {
        return driverService.GetDriverById(driverId);
    }
    
    @PostMapping("/driver")
    public Driver save(@RequestBody  Driver driver) {
        return driverService.create(driver);
    }
    
    @PutMapping("/driver/{driverId}/udpate/status")
    public Driver updateStatus(@PathVariable UUID driverId,@RequestBody boolean available) {
        return driverService.UpdateStatus(driverId, available);
    }
    
    @PutMapping("/driver/{driverId}/update/location")
    public Driver updateLocation(@PathVariable UUID driverId,@RequestBody  Driver drive) {
        return driverService.updateLocation(driverId, drive.getLocation());
    }
    
}
