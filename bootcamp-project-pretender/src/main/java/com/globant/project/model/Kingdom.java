package com.globant.project.model;

import java.util.List;

/**
 * Kingdom Entity
 * 
 * @author David Acuna
 */
public class Kingdom {	

	private Long id;	
	private String name;
	private String location;	
	private List<House> houses;
	
	public Kingdom() {
		super();
	}

	public Kingdom(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	
	public Kingdom(Long id, String name, String location) {
		this.id = id;
		this.name = name;
		this.location = location;
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
	
	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}
		
}
