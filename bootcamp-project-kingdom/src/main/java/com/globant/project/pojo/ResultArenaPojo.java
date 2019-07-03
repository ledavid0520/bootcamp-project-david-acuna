package com.globant.project.pojo;

import com.globant.project.model.Result;

/**
 * Result Entity Pojo
 * 		It is used to return only the valuable information of the entity and avoid circular dependencies
 * 		It includes player info
 * 
 * @author David Acuna
 */
public class ResultArenaPojo {
	
	private Long id;
	private Integer value;
	private Integer game;
	private PlayerPojo player;
	
	public ResultArenaPojo() {
		super();
	}
	
	public ResultArenaPojo(Result result) {
		this.id = result.getId();
		this.value = result.getValue();
		this.game = result.getGame();
		this.player = PlayerPojo.getPlayerPojo(result.getPlayer());
	}
	
	public static ResultArenaPojo getResultPojo(Result result) {
		return result != null ? new ResultArenaPojo(result) : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getGame() {
		return game;
	}

	public void setGame(Integer game) {
		this.game = game;
	}

	public PlayerPojo getPlayer() {
		return player;
	}

	public void setPlayer(PlayerPojo player) {
		this.player = player;
	}	

}
