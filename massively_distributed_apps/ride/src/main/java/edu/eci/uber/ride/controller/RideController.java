package edu.eci.uber.ride.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.eci.uber.ride.domain.Ride;
import edu.eci.uber.ride.domain.RideDto;
import edu.eci.uber.ride.domain.RideRequest;
import edu.eci.uber.ride.service.RideService;


@RestController
public class RideController {
	
	@Autowired
    private RideService rideService;
	
	@GetMapping()
    public String test() {
        return "Test";
    }

	@GetMapping("/rides/options")
    public List<RideRequest> GetOptionsRides(@RequestParam float startingx, @RequestParam float startingy, @RequestParam float endingx, @RequestParam float endingy) {
		float[] start = new float[] {startingx,startingy};
		float[] end = new float[] {endingx,endingy};
		return rideService.GetOptionsRides(start, end);
    }
	
	@GetMapping("/rides")
    public List<Ride> GetOptionsRides() {
		return rideService.getAll();
    }
	
	@PostMapping("/ride")
	public Ride Create(@RequestBody RideDto rideDto) {
		return rideService.Create(rideDto.getUserId(), rideDto.getDriverId(), rideDto.getStartingPoint(), rideDto.getEndingPoint());
		
	}
	
	@PutMapping("/ride/{rideId}/finish")
    public Ride finish(@PathVariable UUID rideId) {
        return rideService.finish(rideId);
    }
	
	@DeleteMapping("/ride/{rideId}")
    public void Delete(@PathVariable UUID rideId) {
        rideService.Delete(rideId);
    }

}
