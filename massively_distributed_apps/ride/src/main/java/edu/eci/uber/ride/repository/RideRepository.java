package edu.eci.uber.ride.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.eci.uber.ride.domain.Ride;


public interface RideRepository extends MongoRepository<Ride, UUID>{
	Optional<Ride> findById(UUID id);
}
