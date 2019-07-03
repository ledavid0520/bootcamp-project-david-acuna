package com.globant.project.pojo;

import com.globant.project.model.Kingdom;

/**
 * Kingdom Entity Request Pojo
 * 		It is used for a user to send only the information necessary for the creation or update of the entity
 * 		The relationship with other entities is done only through the id to ensure a single version of the information
 * 
 * @author David Acuna
 */
public class KingdomRequestPojo {
	
	private Long id;
	private String name;	
	private String location;
	
	public KingdomRequestPojo(Kingdom kingdom) {
    	this.id = kingdom.getId();
		this.name = kingdom.getName();
		this.location = kingdom.getLocation();
	}  
    	
	public KingdomRequestPojo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}	

}
