package com.globant.project.pojo;

import com.globant.project.model.Kingdom;

/**
 * Kingdom Entity Pojo
 * 		It is used to return only the valuable information of the entity and avoid circular dependencies
 * 
 * @author David Acuna
 */
public class KingdomPojo {
	
	private Long id;
	private String name;
	private String location;
	
	public KingdomPojo(Kingdom kingdom) {
		this.id = kingdom.getId();
		this.name = kingdom.getName();
		this.location = kingdom.getLocation();
	}	
	
	public KingdomPojo() {
		super();
	}

	public static KingdomPojo getKingdomPojo(Kingdom kingdom) {
		return kingdom != null ? new KingdomPojo(kingdom) : null;
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
