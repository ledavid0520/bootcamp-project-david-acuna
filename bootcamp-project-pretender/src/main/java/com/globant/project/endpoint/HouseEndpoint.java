package com.globant.project.endpoint;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.HouseRequestPojo;
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.utils.HeaderUtils;

/**
 * House endpoint
 * 
 * @author David Acuna
 */
@RestController
@RequestMapping("/houses")
public class HouseEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(HouseEndpoint.class);
	
	private final RestTemplate restTemplate;
	
	public HouseEndpoint(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }	
	
	/**
	 * Endpoint that returns all houses
	 * 
	 * @return houses
	 * 			  list of houses
	 */
	@GetMapping(produces="application/json")
	public ResponseEntity<List<HousePojo>> getHouses(HttpServletRequest request) {
		
		try {			
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses";
			
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
	 * Endpoint that returns a House
	 * 
	 * @param houseId
	 *            house id
	 * @return housePojo
	 * 			  house with id houseId
	 */
	@GetMapping("/{houseId}")
	public ResponseEntity<HousePojo> getHouse(HttpServletRequest request, @PathVariable("houseId") Long houseId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses/" + houseId;			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, new URI(url));
			
			return restTemplate.exchange(requestEntity, HousePojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that creates a House
	 * 
	 * @param house
	 *            house to be added
	 * @return HousePojo
	 * 			  HousePojo with generated id
	 */
	@PostMapping(produces="application/json")
	public ResponseEntity<HousePojo> addHouse(HttpServletRequest request, @RequestBody @Valid HouseRequestPojo house) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses";			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(house, headers,HttpMethod.POST, new URI(url));
			
			return restTemplate.exchange(requestEntity, HousePojo.class);		
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that updates a House
	 * 
	 * @param houseId
	 * 			id of the house to update  
	 * @param house
	 *          house to be updated	       
	 * @return HousePojo
	 * 			updated house
	 */
	@PutMapping(value="/{houseId}", produces="application/json")
	public ResponseEntity<HousePojo> updateHouse(HttpServletRequest request, @PathVariable("houseId") Long houseId ,
				@RequestBody @Valid HouseRequestPojo house) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses/" + houseId;	
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(house, headers,HttpMethod.PUT, new URI(url));
			
			return restTemplate.exchange(requestEntity, HousePojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that deletes a House
	 * 
	 * @param houseId
	 * 			id of the House to delete  	       
	 * @return HttpStatus
	 */
	@DeleteMapping("/{houseId}")
	public ResponseEntity<HttpStatus> deleteHouse(HttpServletRequest request, @PathVariable("houseId") Long houseId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses/" + houseId;
			
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
	 * Endpoint that filters out houses by name and/or sigil and/or kingdomId
	 * 
	 * @param name
	 *            house's name	   
	 * @param sigil
	 *            house's sigil        
	 * @param kingdomId
	 *            house's kingdom
	 * @return HousePojos
	 * 			  houses that satisfy filters
	 */
	@GetMapping(value="/find", produces="application/json")
	public ResponseEntity<List<HousePojo>> findHouses(HttpServletRequest request, @RequestParam(value="name", required=false) String name,
			@RequestParam(value="sigil", required=false) String sigil, @RequestParam(value="kingdomId", required=false) Long kingdomId){
		try {
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses/find/";
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			builder.replaceQueryParam("name", name);
			builder.replaceQueryParam("sigil", sigil);
			builder.replaceQueryParam("kingdomId", kingdomId);
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(null, headers,HttpMethod.GET, builder.build().encode().toUri());			
			
			return restTemplate.exchange(requestEntity,new ParameterizedTypeReference<List<HousePojo>>(){});
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Endpoint that returns Pretenders of a House
	 * 
	 * @param houseId
	 *            house id
	 * @return PretenderPojos
	 * 			  pretenders with houseId id
	 */
	@GetMapping(value="/{houseId}/pretenders", produces = "application/json")
	public ResponseEntity<List<PretenderPojo>> getHousePretenders(HttpServletRequest request, @PathVariable("houseId") Long houseId) {
		
		try {			
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses/"+ houseId +"/pretenders";
			
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
	 * Endpoint that creates two mysterious pretenders and add them to a House
	 * 
	 * @param file
	 * 			  file that contains the mysterious pretenders
	 * @param houseId
	 *            house id
	 * @return PretenderPojos
	 * 			  pretenders added to house with houseId 
	 */
	@PostMapping(value="/{houseId}/mysteriousPretenders")
	public ResponseEntity<List<PretenderPojo>> addMysteriousPretenders (HttpServletRequest request, @RequestBody MultipartFile file,
				@PathVariable("houseId") Long houseId){
		try {
			Resource fileResource = file.getResource();
			
			LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
		    parts.add("file", fileResource);
		    
		    HttpHeaders headers = new HttpHeaders();
		    HeaderUtils.obtainHeaders(headers, request);
			
			String url = "http://localhost:8000/houses/" + houseId + "/mysteriousPretenders";
			
			HttpEntity<LinkedMultiValueMap<String, Object>> body = new HttpEntity<>(parts, headers);
			
			return restTemplate.exchange(new URI(url), HttpMethod.POST, body, new ParameterizedTypeReference<List<PretenderPojo>>(){});			
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	/**
	 * Endpoint that updates specific attributes of a house
	 * 
	 * @param houseId
	 *            house id	            
	 * @param house
	 *            house to be updated
	 * @return HousePojo
	 * 			  updated house
	 */
	@PatchMapping(value="/{houseId}", produces="application/json")
	public ResponseEntity<HousePojo> patchHouse(HttpServletRequest request, @PathVariable("houseId") Long houseId,
				@RequestBody HouseRequestPojo house){
		try {
			RestTemplate restTemplate2 = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			//This request is handled by CustomizeResponseEntityExceptionHandler
			HttpHeaders headers = new HttpHeaders();
			HeaderUtils.obtainHeaders(headers, request);
			String url = "http://localhost:8000/houses/"+ houseId;	
			
			RequestEntity<?>  requestEntity = 
					new RequestEntity<>(house, headers,HttpMethod.PATCH, new URI(url));
			
			return restTemplate2.exchange(requestEntity, HousePojo.class);
		} catch (HttpClientErrorException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
