package com.globant.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globant.project.enums.LevelEnum;

/**
 * Pretender Entity
 * 
 * @author David Acuna
 */
@Table
@Entity
public class Pretender {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@Enumerated(EnumType.STRING)
	private LevelEnum level;
	
	@ManyToOne(targetEntity = House.class)
    private House house;

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
