package edu.eci.uber.user.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import edu.eci.uber.user.domain.User;


public interface UserRepository extends MongoRepository<User, UUID>{
	
	Optional<User> findById(UUID id);
}
