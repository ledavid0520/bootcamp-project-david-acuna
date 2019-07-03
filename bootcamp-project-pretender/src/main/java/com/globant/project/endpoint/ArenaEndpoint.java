package com.globant.project.endpoint;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.globant.project.pojo.ArenaWinnersPojo;
import com.globant.project.pojo.ResultArenaPojo;
import com.globant.project.pojo.ResultPojo;
import com.globant.project.utils.HeaderUtils;


/**
 * Arena endpoint
 * 
 * @author David Acuna
 */
@RestController
@RequestMapping("/arena")
public class ArenaEndpoint {
	
private static final Logger log = LoggerFactory.getLogger(HouseEndpoint.class);
	
	private final RestTemplate restTemplate;
	
	public ArenaEndpoint(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
	/**
	 * Endpoint that shows Arena information so far
	 * 
	 * @return results
	 * 		arena info
	 */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ResultArenaPojo>> getArenaResult(HttpServletRequest request) {  
    	
    	try {	
	    	HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/arena";
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ResultArenaPojo>>(){});
    	} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
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
    public ResponseEntity<ResultPojo> playArena(HttpServletRequest request) {        
    	try {
    		HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/arena/movement";
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, ResultPojo.class);
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
    public ResponseEntity<ArenaWinnersPojo> endArena(HttpServletRequest request) {        
    	try {
    		HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/arena/endArena";
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, ArenaWinnersPojo.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }

}
