package com.globant.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * House Entity
 * 
 * @author David Acuna
 */
@Table
@Entity
public class House {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String sigil;
	
	@OneToMany(targetEntity = Pretender.class, mappedBy = "house")
	private List<Pretender> pretenders;
	
	@ManyToOne(targetEntity = Kingdom.class)
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
	
	public House(Long id, String name, String sigil, Kingdom kingdom) {
		super();
		this.id = id;
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
