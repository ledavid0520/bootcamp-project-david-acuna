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
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.pojo.PretenderRequestPojo;
import com.globant.project.utils.HeaderUtils;

/**
 * Pretender endpoint
 * 
 * @author David Acuna
 */
@RestController
@RequestMapping("/pretenders")
public class PretenderEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(PretenderEndpoint.class);
		
	private final RestTemplate restTemplate;
	
	public PretenderEndpoint(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }	
	
	/**
	 * Endpoint that return a pretender with a given house
	 * 
	 * @param idPretender
	 * 				id of pretender
	 * 
	 * @param idHouse
	 * 				id of House
	 * 
	 * @return pretender_house
	 * 			  pretender of house idHouse
	 */
	@GetMapping(value="{idPretender}/houses/{idHouse}", produces="application/json")
	public ResponseEntity<HousePojo> getPretenderHouse(HttpServletRequest request, @PathVariable("idPretender") Long idPretender,
				@PathVariable("idHouse") Long idHouse) {		
		try {		
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders/"+idPretender+"/houses/"+idHouse;
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, HousePojo.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that returns all pretenders
	 * 
	 * @return pretenders
	 * 			  list of pretenders
	 */
	@GetMapping(produces="application/json")
	public ResponseEntity<List<PretenderPojo>> getPretenders(HttpServletRequest request) {
		
		try {			
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders";
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<PretenderPojo>>(){});
			
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint that returns a Pretender
	 * 
	 * @param pretenderId
	 *            pretender id
	 * @return pretenderPojo
	 * 			  pretender with id pretenderId
	 */
	@GetMapping(value="/{pretenderId}", produces="application/json")
	public ResponseEntity<PretenderPojo> getPretender(HttpServletRequest request, @PathVariable("pretenderId") Long pretenderId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders/" + pretenderId;			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, PretenderPojo.class);		 
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that creates a Pretender
	 * 
	 * @param pretender
	 *            pretender to be added
	 * @return PretenderPojo
	 * 			  PretenderPojo with generated id
	 */
	@PostMapping(produces="application/json")
	public ResponseEntity<PretenderPojo> addPretender(HttpServletRequest request, @RequestBody @Valid PretenderRequestPojo pretender) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders";			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(pretender, headers,HttpMethod.POST, new URI(url));
			
			return restTemplate.exchange(requestEntity, PretenderPojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that updates a Pretender
	 * 
	 * @param pretenderId
	 * 			id of the pretender to update  
	 * @param pretender
	 *          pretender to be updated	       
	 * @return PretenderPojo
	 * 			updated pretender
	 */
	@PutMapping(value="/{pretenderId}", produces="application/json")
	public ResponseEntity<PretenderPojo> updatePretender(HttpServletRequest request, @PathVariable("pretenderId") Long pretenderId ,@RequestBody @Valid PretenderRequestPojo pretender) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders/" + pretenderId;	
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(pretender, headers,HttpMethod.PUT, new URI(url));
			
			return restTemplate.exchange(requestEntity, PretenderPojo.class);
			
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that deletes a Pretender
	 * 
	 * @param pretenderId
	 * 			id of the Pretender to delete  	       
	 * @return HttpStatus
	 */
	@DeleteMapping("/{pretenderId}")
	public ResponseEntity<HttpStatus> deletePretender(HttpServletRequest request, @PathVariable("pretenderId") Long pretenderId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders/" + pretenderId;
			
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
	 * Endpoint that filters out pretenders by name and/or houseId
	 * 
	 * @param name
	 *            pretender's name	            
	 * @param houseId
	 *            pretender's house
	 * @return PretenderPojos
	 * 			  pretenders that satisfy filters
	 */
	@GetMapping(value="/find", produces="application/json")
	public ResponseEntity<List<PretenderPojo>> findPretenders(HttpServletRequest request, @RequestParam(value="name", required=false) String name,
			@RequestParam(value="level", required=false) String level, @RequestParam(value="houseId", required=false) Long houseId){
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders/find/";
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			builder.replaceQueryParam("name", name);
			builder.replaceQueryParam("level", level);
			builder.replaceQueryParam("houseId", houseId);
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, builder.build().encode().toUri());			
			
			return restTemplate.exchange(requestEntity,new ParameterizedTypeReference<List<PretenderPojo>>(){});
			
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that updates specific attributes of a pretender
	 * 
	 * @param pretenderId
	 *            pretender id	            
	 * @param pretender
	 *            pretender to be updated
	 * @return PretenderPojo
	 * 			  updated pretender
	 */
	@PatchMapping(value="/{pretenderId}", produces="application/json")
	public ResponseEntity<PretenderPojo> patchPretender(HttpServletRequest request, @PathVariable("pretenderId") Long pretenderId,
				@RequestBody PretenderRequestPojo pretender){
		try {
			RestTemplate restTemplate2 = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			//This request is handled by CustomizeResponseEntityExceptionHandler
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/pretenders/"+ pretenderId;	
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(pretender, headers,HttpMethod.PATCH, new URI(url));
			
			return restTemplate2.exchange(requestEntity, PretenderPojo.class);	
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
