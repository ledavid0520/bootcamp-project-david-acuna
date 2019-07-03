package com.globant.project.endpoint;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.KingdomPojo;
import com.globant.project.pojo.KingdomRequestPojo;
import com.globant.project.utils.HeaderUtils;

/**
 * Kingdom endpoint
 * 
 * @author David Acuna
 */
@RestController
@RequestMapping("/kingdoms")
public class KingdomEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(KingdomEndpoint.class);
	
	private final RestTemplate restTemplate;
	
	public KingdomEndpoint(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }	
	
	/**
	 * Endpoint that returns the list of Kingdoms
	 * 
	 * @return kingdomPojos
	 * 			  list of kingdoms
	 */	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<KingdomPojo>> getKingdoms(HttpServletRequest request) {
		
		try {	
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms";			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<KingdomPojo>>(){});
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that returns a Kingdom
	 * 
	 * @param kingdomId
	 *            kingdom id
	 * @return kingdomPojo
	 * 			  kingdomPojo with id kingdomId
	 */
	@GetMapping(value="/{kingdomId}", produces = "application/json")
	public ResponseEntity<KingdomPojo> getKingdom(HttpServletRequest request, @PathVariable("kingdomId") Long kingdomId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms/" + kingdomId;			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, KingdomPojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that creates a Kingdom
	 * 
	 * @param kingdom
	 *            kingdom to be added
	 * @return kingdomPojo
	 * 			  kingdomPojo with generated id
	 */
	@PostMapping(produces="application/json")
	public ResponseEntity<KingdomPojo> addKingdom(HttpServletRequest request, @RequestBody @Valid KingdomRequestPojo kingdom) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms";			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(kingdom, headers,HttpMethod.POST, new URI(url));
			
			return restTemplate.exchange(requestEntity, KingdomPojo.class);		
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that updates a Kingdom
	 * 
	 * @param kingdomId
	 * 			id of the kingdom to update  
	 * @param kingdom
	 *          kingdom to be updated	       
	 * @return kingdomPojo
	 * 			updated kingdom
	 */
	@PutMapping(value="/{kingdomId}", produces="application/json")
	public ResponseEntity<KingdomPojo> updateKingdom(HttpServletRequest request, @PathVariable("kingdomId") Long kingdomId ,@RequestBody @Valid KingdomRequestPojo kingdom) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms/" + kingdomId;	
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(kingdom, headers,HttpMethod.PUT, new URI(url));
			
			return restTemplate.exchange(requestEntity, KingdomPojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that deletes a Kingdom
	 * 
	 * @param kingdomId
	 * 			id of the kingdom to delete  	       
	 * @return HttpStatus
	 */
	@DeleteMapping("/{kingdomId}")
	public ResponseEntity<HttpStatus> deleteKingdom(HttpServletRequest request, @PathVariable("kingdomId") Long kingdomId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms/" + kingdomId;
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.DELETE, new URI(url));
			
			return restTemplate.exchange(requestEntity, HttpStatus.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that returns Houses of a Kingdom
	 * 
	 * @param kingdomId
	 *            kingdom id
	 * @return housePojos
	 * 			  kingdom kingdomId houses
	 */
	@GetMapping(value="/{kingdomId}/houses", produces = "application/json")
	public ResponseEntity<List<HousePojo>> getKingdomHouses(HttpServletRequest request, @PathVariable("kingdomId") Long kingdomId) {
		
		try {			
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms/"+ kingdomId +"/houses";
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<HousePojo>>(){});
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that filters out kingdoms by name and/or location
	 * 
	 * @param name
	 *            kingdom's name	          
	 * @param location
	 *            kingdom's location
	 * @return KingdomPojos
	 * 			  kingdoms that satisfy filters
	 */
	@GetMapping(value="/find", produces = "application/json")
	public ResponseEntity<List<KingdomPojo>> findKingdoms(HttpServletRequest request, @RequestParam(value="name", required=false) String name,
				@RequestParam(value="location", required=false) String location){
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms/find/";
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			builder.replaceQueryParam("name", name);
			builder.replaceQueryParam("location", location);
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, builder.build().encode().toUri());			
			
			return restTemplate.exchange(requestEntity,new ParameterizedTypeReference<List<KingdomPojo>>(){});
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			 log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that updates specific attributes of a kingdom
	 * 
	 * @param kingdomId
	 *            kingdom id	            
	 * @param kingdom
	 *            kingdom to be updated
	 * @return KingdomPojo
	 * 			  updated kingdom
	 */
	@PatchMapping(value="/{kingdomId}", produces="application/json")
	public ResponseEntity<KingdomPojo> patchKingdom(HttpServletRequest request, @PathVariable("kingdomId") Long kingdomId,
				@RequestBody KingdomRequestPojo kingdom){
		try {
			RestTemplate restTemplate2 = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			//This request is handled by CustomizeResponseEntityExceptionHandler
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/kingdoms/"+ kingdomId;	
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(kingdom, headers,HttpMethod.PATCH, new URI(url));
			
			return restTemplate2.exchange(requestEntity, KingdomPojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
