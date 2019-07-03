package com.globant.project.model;

/**
 * Result Entity
 * 
 * @author David Acuna
 */
public class Result {
	
	private Long id;
    private Player player;
    private Integer value;
    private Integer game;

	public Result() {
		super();
	}

	public Result(Player player, Integer value, Integer game) {
		super();
		this.player = player;
		this.value = value;
		this.game = game;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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

}
