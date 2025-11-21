package edu.eci.CloudPractice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import edu.eci.CloudPractice.controller.dto.ClientDto;
import edu.eci.CloudPractice.domain.entities.Client;
import edu.eci.CloudPractice.services.ClientService;

@RestController
public class ClientController {
	
	@Autowired
    private ClientService clienteService;
	
	@GetMapping()
    public String test() {
    	
        return "Test";
    }
	
    @GetMapping("/clients")
    public List<Client> getAll() {
    	
        return clienteService.getAll();
    }
    
    @PostMapping("/client")
    public Client save(@RequestBody  ClientDto clientDto) {
    	Client client = new Client(clientDto.getName());
        return clienteService.create(client);
    }
}
