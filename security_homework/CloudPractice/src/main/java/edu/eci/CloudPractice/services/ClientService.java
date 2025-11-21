package edu.eci.CloudPractice.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.eci.CloudPractice.domain.entities.Client;
import java.util.HashMap;

@Service
public class ClientService {
	
	private HashMap<UUID, Client> clientMap = new HashMap<>();
	
	public HashMap<UUID, Client> getAll(){
		return clientMap;
	}
	
    public Client create(Client client) {
    	client.setId(UUID.randomUUID());
    	clientMap.put(client.getId(), client);
    	return clientMap.get(client.getId());
    }
}
