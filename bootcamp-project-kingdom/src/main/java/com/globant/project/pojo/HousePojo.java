package com.globant.project.pojo;

import com.globant.project.model.House;

/**
 * House Entity Pojo
 * 		It is used to return only the valuable information of the entity and avoid circular dependencies
 * 
 * @author David Acuna
 */
public class HousePojo {
	
	private Long id;	
	private String name;		
	private String sigil;	
	private KingdomPojo kingdom;
	
	public HousePojo(House house) {
		this.id = house.getId();
		this.name = house.getName();
		this.sigil = house.getSigil();
		this.kingdom = KingdomPojo.getKingdomPojo(house.getKingdom());
	}
		
	public HousePojo() {
		super();
	}
	
	public static HousePojo getHousePojo(House house) {
		return house != null ? new HousePojo(house) : null;
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

	public KingdomPojo getKingdom() {
		return kingdom;
	}

	public void setKingdom(KingdomPojo kingdom) {
		this.kingdom = kingdom;
	}
	
}
