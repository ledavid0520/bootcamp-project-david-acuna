package com.globant.project.pojo;

/**
 * TableResult Pojo Entity
 * 
 * @author David Acuna
 */
public class TableResultPojo {

	private Long player;
	private Integer game;
	private Double average;	
	
	public TableResultPojo() {
		super();
	}

	public TableResultPojo(Long player, Integer game, Double average) {
		this.player = player;
		this.game = game;
		this.average = average;
	}

	public Long getPlayer() {
		return player;
	}

	public void setPlayer(Long player) {
		this.player = player;
	}

	public Integer getGame() {
		return game;
	}

	public void setGame(Integer game) {
		this.game = game;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}	
	
}
