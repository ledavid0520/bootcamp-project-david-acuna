package com.globant.project.model;

import com.globant.project.enums.LevelEnum;

/**
 * Pretender Entity
 * 
 * @author David Acuna
 */
public class Pretender {	

	private Long id;
	private String name;	
    private House house;
	private LevelEnum level;

	public Pretender() {
		super();
	}
	
	public Pretender(Long id, String name, House house, LevelEnum level) {
		this.id = id;
		this.name = name;
		this.house = house;
		this.level = level;
	}

	public Pretender(String name, House house, LevelEnum level) {
		super();
		this.name = name;
		this.house = house;
		this.level = level;
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

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}	
	
	public LevelEnum getLevel() {
		return level;
	}

	public void setLevel(LevelEnum level) {
		this.level = level;
	}
			
}
