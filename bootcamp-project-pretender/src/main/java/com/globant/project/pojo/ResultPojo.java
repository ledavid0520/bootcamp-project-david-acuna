package com.globant.project.pojo;

import com.globant.project.model.Result;

/**
 * Result Entity Pojo
 * 		It is used to return only the valuable information of the entity and avoid circular dependencies
 * 
 * @author David Acuna
 */
public class ResultPojo {
	
	private Long id;
	private Integer value;
	private Integer game;
	
	public ResultPojo() {
		super();
	}
	
	public ResultPojo(Result result) {
		this.id = result.getId();
		this.value = result.getValue();
		this.game = result.getGame();
	}
	
	public static ResultPojo getResultPojo(Result result) {
		return result != null ? new ResultPojo(result) : null;
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

}
