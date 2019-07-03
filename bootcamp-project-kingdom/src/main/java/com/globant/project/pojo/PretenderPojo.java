package com.globant.project.pojo;

import com.globant.project.model.Pretender;

/**
 * Pretender Entity Pojo
 * 		It is used to return only the valuable information of the entity and avoid circular dependencies
 * 
 * @author David Acuna
 */
public class PretenderPojo {
	
	private Long id;
	private String name;	
    private HousePojo house;
    private String level;
	
    public PretenderPojo(Pretender pretender) {
		this.id = pretender.getId();
		this.name = pretender.getName();
		this.house = HousePojo.getHousePojo(pretender.getHouse());
		this.level = pretender.getLevel().getLevel();
	}
	
	public PretenderPojo() {
		super();
	}
	
	public static PretenderPojo getPretenderPojo(Pretender pretender) {
		return pretender != null ? new PretenderPojo(pretender) : null;
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

	public HousePojo getHouse() {
		return house;
	}

	public void setHouse(HousePojo house) {
		this.house = house;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
			
}
