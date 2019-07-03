package com.globant.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Player Entity
 * 
 * @author David Acuna
 */
@Table
@Entity
public class Player {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@OneToOne(targetEntity = Kingdom.class)
	private Kingdom kingdom;	

	public Player() {
		super();
	}
	
	public Player(String name, Kingdom kingdom) {
		super();
		this.name = name;
		this.kingdom = kingdom;
	}

	public Player(Long id, String name, Kingdom kingdom) {
		super();
		this.id = id;
		this.name = name;
		this.kingdom = kingdom;
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

	public Kingdom getKingdom() {
		return kingdom;
	}

	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
	}
	
}
