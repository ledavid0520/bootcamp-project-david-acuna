package com.globant.project.pojo;

import java.util.List;

public class ArenaWinnersPojo {
	
	private List<TableResultPojo> arenaResults;
	private List<TableResultPojo> winnersByGame;
	private PlayerPojo winner;
	
	
	public ArenaWinnersPojo(List<TableResultPojo> arenaResults, List<TableResultPojo> winnersByGame,
			PlayerPojo winner) {
		super();
		this.arenaResults = arenaResults;
		this.winnersByGame = winnersByGame;
		this.winner = winner;
	}


	public List<TableResultPojo> getArenaResults() {
		return arenaResults;
	}


	public void setArenaResults(List<TableResultPojo> arenaResults) {
		this.arenaResults = arenaResults;
	}


	public List<TableResultPojo> getWinnersByGame() {
		return winnersByGame;
	}


	public void setWinnersByGame(List<TableResultPojo> winnersByGame) {
		this.winnersByGame = winnersByGame;
	}


	public PlayerPojo getWinner() {
		return winner;
	}


	public void setWinner(PlayerPojo winner) {
		this.winner = winner;
	}	

}
