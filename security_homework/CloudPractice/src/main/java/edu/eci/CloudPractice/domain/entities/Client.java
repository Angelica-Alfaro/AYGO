package edu.eci.CloudPractice.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "clients")
public class Client {
	
	@Id
	private UUID id;
	private String name;
	private LocalDateTime date;  
	
	public Client() {}
	
	public Client(String name) {
		this.name = name;
		this.date = LocalDateTime.now();
	}

	public String getClientName() {
		return name;
	}
	
	public void setClientName(String clientName) {
		this.name = clientName;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID uuid) {
		this.id = uuid;
	}	
}
