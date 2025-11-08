package edu.eci.uber.driver.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import edu.eci.uber.driver.domain.Driver;


public interface DriverRepository extends MongoRepository<Driver, UUID>{

	Optional<Driver> findById(UUID id);
}
