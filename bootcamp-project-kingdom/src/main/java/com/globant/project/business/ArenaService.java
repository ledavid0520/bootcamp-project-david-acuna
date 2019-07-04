package com.globant.project.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globant.project.enums.LevelEnum;
import com.globant.project.model.Arena;
import com.globant.project.model.Kingdom;
import com.globant.project.model.Player;
import com.globant.project.model.Pretender;
import com.globant.project.model.Result;
import com.globant.project.pojo.ArenaWinnersPojo;
import com.globant.project.pojo.PlayerPojo;
import com.globant.project.pojo.ResultArenaPojo;
import com.globant.project.pojo.ResultPojo;
import com.globant.project.pojo.TableResultPojo;
import com.globant.project.repository.PlayerRepository;
import com.globant.project.repository.ResultRepository;
import com.globant.project.utils.AuthContextUtil;
import com.globant.project.utils.GeneratorUtils;

/**
 * Manage Arena business logic
 * 
 * @author David Acuna
 */
@Service
public class ArenaService {
	
	@Autowired
	private ResultRepository resultRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	public ArenaService(ResultRepository resultRepository) {
		this.resultRepository = resultRepository;
	}
	
	/**
	 * Shows Arena information so far
	 * 
	 * @param arena
	 * 		arena
	 * 
	 * @return results
	 * 		arena info
	 */
	public List<ResultArenaPojo> getResults(Arena arena) {
		List<Result> results = arena.getResults();
		return results.stream()
				.map(r -> ResultArenaPojo.getResultPojo(r))
				.collect(Collectors.toList());
	}
	
	/**
	 * Makes a player movement in the arena.
	 * Validates if player has more attempts in the current game,otherwise generates new game int,
	 * Generates the value of the current attempt considering the max level of the pretender of his kingdom,
	 * And creates a new result from the collected information of the movement 
	 * @param arena
	 * 		arena
	 * 
	 * @return resultPojo
	 * 		movement info
	 */
	public ResultPojo play(Arena arena) {

		String player = AuthContextUtil.getUser();		
		Result result;
		Player playerArena = playerRepository.findByName(player);
		Integer currentGame = resultRepository.getMaxPlayerGame(playerArena);	
		
		Integer value = GeneratorUtils.generateNumber(getBestPretender(playerArena.getKingdom()));		
		Integer currentGameSize = resultRepository.countMaxGameByUser(playerArena);
		
		switch (currentGameSize) {
		case 0:
			result = new Result(playerArena, value, 1);
			break;
		case 1:
		case 2:
			result = new Result(playerArena, value, currentGame);
			break;
		default:
			result = new Result(playerArena, value, currentGame+1);
			break;
		}
		
		resultRepository.save(result);
		arena.getResults().add(result);
		
		return ResultPojo.getResultPojo(result);		
	}
	
	/**
	 * Checks if current user has a Platinum Pretender in his kingdom 
	 * 
	 * @param kingdom
	 * 		player kingdom
	 * 
	 * @return true if user has a Platinum Pretender, false otherwise
	 */
	public Boolean getBestPretender(Kingdom kingdom) {
		
		Predicate<Pretender> pretenderPLATINUM = p -> p.getLevel().equals(LevelEnum.PLATINUM);
		
		return kingdom.getHouses()
				.stream()
				.flatMap(house -> house.getPretenders().stream())
				.anyMatch(pretenderPLATINUM);
	}

	/**
	 * Ends arena and shows final results.
	 * Generates the table of the arena results by player in each game,
	 * Lists the winner per game,
	 * And gets the arena winner
	 * 
	 * @param arena
	 * 		arena
	 * 
	 * @return arenaResultPojo
	 * 		info about the arena results
	 */
	public ArenaWinnersPojo endArena(Arena arena) {
		List<TableResultPojo> arenaResults = resultRepository.generateFinalTable();
		
		List<TableResultPojo> winnersByGame = arenaResults.stream()
				.collect(Collectors
						.groupingBy(TableResultPojo::getGame, 
								Collectors.maxBy(Comparator.comparing(TableResultPojo::getAverage)))
                ).values().stream()
				.map(e -> e.get())
				.collect(Collectors.toList());
		
		Long winnerId = winnersByGame.stream()
						.collect(Collectors.groupingBy(TableResultPojo::getPlayer, Collectors.counting()))
						.entrySet().stream().max(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey).orElse(null);	
		
		endCurrentArena(arena);
		return new ArenaWinnersPojo(arenaResults, winnersByGame, PlayerPojo.getPlayerPojo(playerRepository.getById(winnerId)));
	}

	/**
	 * Ends arena.
	 * 
	 * @param arena
	 * 		arena
	 */
	private void endCurrentArena(Arena arena) {
		arena.setResults(new ArrayList<Result>());	
		resultRepository.deleteAll();
	}	

}
