package com.globant.project.model;

/**
 * Player Entity
 * 
 * @author David Acuna
 */
public class Player {
	

	private Long id;
	private String name;
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
