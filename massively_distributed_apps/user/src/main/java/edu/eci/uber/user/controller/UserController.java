package edu.eci.uber.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.uber.user.domain.RideDto;
import edu.eci.uber.user.domain.RideRequest;
import edu.eci.uber.user.domain.User;
import edu.eci.uber.user.service.UserService;


@RestController
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping()
    public String test() {
    	
        return "Test";
    }
	
    @GetMapping("/users")
    public List<User> getAll() {
    	
        return userService.getAll();
    }
    
    @PostMapping("/user")
    public User save(@RequestBody User user) {
        return userService.create(user);
    }
    
    @GetMapping("/user/{userId}/destination")
    public List<RideRequest> GetOptionsRide(@PathVariable  UUID userId,@RequestParam float endPointx,@RequestParam float endPointy) {
        float[] destination = new float[] {endPointx,endPointy};
    	return userService.GetOptionsRide(userId,destination);
    }
    
    @PostMapping("/user/{userId}/askTravel")
    public String AskTravel(@PathVariable  UUID userId, @RequestBody RideDto rideDto) {
    	userService.AskTravel(userId,rideDto.getDriverId(),rideDto.getEndingPoint());
    	return "OK";
    }

}
