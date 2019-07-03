package com.globant.project.pojo;

import com.globant.project.model.Pretender;

/**
 * Pretender Entity Request Pojo
 * 		It is used for a user to send only the information necessary for the creation or update of the entity
 * 		The relationship with other entities is done only through the id to ensure a single version of the information
 * 
 * @author David Acuna
 */
public class PretenderRequestPojo {
	
	private Long id;
	private String name;	
    private Long houseId;
    private String level;
	
    public PretenderRequestPojo(Pretender pretender) {
    	this.id = pretender.getId();
		this.name = pretender.getName();
		this.houseId = pretender.getHouse() != null ? pretender.getHouse().getId() : null;
		this.level = pretender.getLevel().getLevel();
	}  
    	
	public PretenderRequestPojo() {
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

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
			
}
