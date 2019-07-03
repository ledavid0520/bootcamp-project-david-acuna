package com.globant.project.endpoint;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.globant.project.business.KingdomService;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.KingdomPojo;
import com.globant.project.pojo.KingdomRequestPojo;

import io.swagger.annotations.Api;

/**
 * Kingdom endpoint
 * 
 * @author David Acuna
 */
@Api(value="KingdomEndpoint", description = "Kingdom EndPoint")
@RestController
@RequestMapping("/kingdoms")
public class KingdomEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(KingdomEndpoint.class);
	
	@Autowired
	private KingdomService kingdomService;
	
	/**
	 * Endpoint that returns the list of Kingdoms
	 * 
	 * @return kingdomPojos
	 * 			  list of kingdoms
	 */	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<KingdomPojo>> getKingdoms() {
		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(kingdomService.getKingdoms());
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
	public ResponseEntity<KingdomPojo> getKingdom(@PathVariable("kingdomId") Long kingdomId) {
		
		try {
			final KingdomPojo kingdomPojo = kingdomService.getKingdom(kingdomId);			
			if(Objects.nonNull(kingdomPojo)) {
				return ResponseEntity.status(HttpStatus.OK).body(kingdomPojo);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
	public ResponseEntity<KingdomPojo> addKingdom(@RequestBody @Valid KingdomRequestPojo kingdom) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(kingdomService.addKingdom(kingdom));		
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
	public ResponseEntity<KingdomPojo> updateKingdom(@PathVariable("kingdomId") Long kingdomId ,@RequestBody @Valid KingdomRequestPojo kingdom) {
		
		try {
			KingdomPojo k = kingdomService.updateKingdom(kingdomId, kingdom);
			if(Objects.nonNull(k)) {
				return ResponseEntity.status(HttpStatus.OK).body(k);	
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(k);
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
	public ResponseEntity<HttpStatus> deleteKingdom(@PathVariable("kingdomId") Long kingdomId) {
		
		try {
			HttpStatus s = kingdomService.deleteKingdom(kingdomId);
			return ResponseEntity.status(s).build();
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
	public ResponseEntity<List<HousePojo>> getKingdomHouses(@PathVariable("kingdomId") Long kingdomId) {
		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(kingdomService.getKingdomHouses(kingdomId));
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
	public ResponseEntity<List<KingdomPojo>> findKingdoms(@RequestParam(value="name", required=false) String name,
				@RequestParam(value="location", required=false) String location){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(kingdomService.findKingdoms(name, location));
		}catch (Exception e) {
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
	public ResponseEntity<KingdomPojo> patchKingdom(@PathVariable("kingdomId") Long kingdomId,
				@RequestBody KingdomRequestPojo kingdom){
		try {
			KingdomPojo k = kingdomService.updateKingdomField(kingdomId, kingdom);
			if(Objects.nonNull(k)) {
				return ResponseEntity.status(HttpStatus.OK).body(k);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
