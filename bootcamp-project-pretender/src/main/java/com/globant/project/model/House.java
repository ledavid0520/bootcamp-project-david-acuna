package com.globant.project.model;

import java.util.List;

/**
 * House Entity
 * 
 * @author David Acuna
 */
public class House {	
	
	private Long id;	
	private String name;		
	private String sigil;		
	private List<Pretender> pretenders;
	private Kingdom kingdom;

	public House() {
		super();
	}
	
	public House(String name, String sigil, Kingdom kingdom) {
		super();
		this.name = name;
		this.sigil = sigil;
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

	public String getSigil() {
		return sigil;
	}

	public void setSigil(String sigil) {
		this.sigil = sigil;
	}

	public List<Pretender> getPretenders() {
		return pretenders;
	}

	public void setPretenders(List<Pretender> pretenders) {
		this.pretenders = pretenders;
	}	
	
	public Kingdom getKingdom() {
		return kingdom;
	}

	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
	}
			
}
