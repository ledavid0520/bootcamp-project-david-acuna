package com.globant.project.pojo;

import com.globant.project.model.House;

/**
 * House Entity Request Pojo
 * 		It is used for a user to send only the information necessary for the creation or update of the entity
 * 		The relationship with other entities is done only through the id to ensure a single version of the information
 * 
 * @author David Acuna
 */
public class HouseRequestPojo {
	
	private Long id;
	private String name;
	private String sigil;
    private Long kingdomId;
    
	public HouseRequestPojo() {
		super();
	}

	public HouseRequestPojo(House house) {
		super();
		this.id = house.getId();
		this.name = house.getName();
		this.sigil = house.getSigil();
		this.kingdomId = house.getKingdom() != null ? house.getKingdom().getId() : null;
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

	public String getSigil() {
		return sigil;
	}

	public void setSigil(String sigil) {
		this.sigil = sigil;
	}

	public Long getKingdomId() {
		return kingdomId;
	}

	public void setKingdomId(Long kingdomId) {
		this.kingdomId = kingdomId;
	}

}
