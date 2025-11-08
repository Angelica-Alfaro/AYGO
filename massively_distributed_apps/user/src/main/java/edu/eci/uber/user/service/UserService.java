package edu.eci.uber.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.uber.user.clients.RideClient;
import edu.eci.uber.user.domain.RideDto;
import edu.eci.uber.user.domain.RideRequest;
import edu.eci.uber.user.domain.User;
import edu.eci.uber.user.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RideClient rideClient;
	
	public UserService(@Autowired UserRepository userRepository, RideClient rideClient) {
		this.userRepository = userRepository;
		this.rideClient = rideClient;
	}
	
	public User create(User user) {
		user.setId(UUID.randomUUID());
		userRepository.save(user);
    	return GetUserById(user.getId());
    }
	
	public User GetUserById(UUID driverId) {
		return userRepository.findById(driverId).get();
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}

	public List<RideRequest> GetOptionsRide(UUID userId, float[] destination) {
		User user = GetUserById(userId);
		List<RideRequest> rides = rideClient.GetOptionsRide(user.getLocation(),destination);

		return rides;
	}
	
	public void AskTravel(UUID userId, UUID driverId,float[] destination) {
		User user = GetUserById(userId);
		rideClient.Create(new RideDto(user.getLocation(),destination,userId,driverId));
	}
}
