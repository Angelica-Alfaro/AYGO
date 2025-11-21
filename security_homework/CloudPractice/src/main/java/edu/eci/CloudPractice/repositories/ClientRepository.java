package edu.eci.CloudPractice.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.CloudPractice.domain.entities.Client;

public interface ClientRepository extends MongoRepository<Client, UUID>{
	
	Optional<Client> findById(UUID id);
}
