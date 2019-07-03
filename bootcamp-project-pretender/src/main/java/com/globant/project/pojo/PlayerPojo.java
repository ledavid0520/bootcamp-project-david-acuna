package com.globant.project.pojo;

import com.globant.project.model.Player;

/**
 * Player Entity Pojo
 * 		It is used to return only the valuable information of the entity and avoid circular dependencies
 * 
 * @author David Acuna
 */
public class PlayerPojo {
	
	private Long id;
	private String name;
	private String kingdom;
	
	public PlayerPojo() {
		super();
	}
	
	public PlayerPojo(Player player) {
		this.id = player.getId();
		this.name = player.getName();
		this.kingdom = player.getKingdom().getName();
	}
	
	public static PlayerPojo getPlayerPojo(Player player) {
		return player != null ? new PlayerPojo(player) : null;
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

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}		

}
