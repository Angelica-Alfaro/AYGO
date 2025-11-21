package edu.eci.CloudPractice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import edu.eci.CloudPractice.domain.entities.Client;
import edu.eci.CloudPractice.repositories.ClientRepository;

@Service
public class ClientService {
	
	private final ClientRepository clientRepository;
	
	public ClientService(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
	
	public List<Client> getAll(){
		
		return clientRepository.findAll();
	}
	
    public Client create(Client client) {
    	client.setId(UUID.randomUUID());
    	clientRepository.save(client);
    	return clientRepository.findById(client.getId()).get();
    }
}
