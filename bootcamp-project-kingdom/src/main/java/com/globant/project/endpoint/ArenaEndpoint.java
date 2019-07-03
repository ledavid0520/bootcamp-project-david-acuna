package com.globant.project.endpoint;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globant.project.business.ArenaService;
import com.globant.project.model.Arena;
import com.globant.project.pojo.ArenaWinnersPojo;
import com.globant.project.pojo.ResultArenaPojo;
import com.globant.project.pojo.ResultPojo;

import io.swagger.annotations.Api;

/**
 * Arena endpoint
 * 
 * @author David Acuna
 */
@Api(value="ArenaEndpoint", description = "Arena EndPoint")
@RestController
@RequestMapping("/arena")
public class ArenaEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(HouseEndpoint.class);
	
	@Autowired
    @Qualifier(value="arenaSingleton")
    private Arena arena;
	
	@Autowired
	private ArenaService arenaService;
	
	/**
	 * Endpoint that shows Arena information so far
	 * 
	 * @return results
	 * 		arena info
	 */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ResultArenaPojo>> getArenaResults() {        
    	try {
    		return ResponseEntity.status(HttpStatus.OK).body(arenaService.getResults(arena));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    } 
    
    /**
	 * Endpoint that makes a player movement in the arena
	 * 
	 * @return resultPojo
	 * 		movement info
	 */
    @GetMapping(value="/movement", produces = "application/json")
    public ResponseEntity<ResultPojo> playArena() {        
    	try {
    		return ResponseEntity.status(HttpStatus.OK).body(arenaService.play(arena));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    } 
    
    /**
	 * Endpoint that ends arena and shows final results
	 * 
	 * @return resultPojo
	 * 		movement info
	 */
    @GetMapping(value="/endArena", produces = "application/json")
    public ResponseEntity<ArenaWinnersPojo> endArena() {        
    	try {
    		return ResponseEntity.status(HttpStatus.OK).body(arenaService.endArena());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }

}
