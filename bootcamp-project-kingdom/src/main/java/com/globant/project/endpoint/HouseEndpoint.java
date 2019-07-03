package com.globant.project.endpoint;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.globant.project.business.HouseService;
import com.globant.project.business.PretenderService;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.HouseRequestPojo;
import com.globant.project.pojo.PretenderPojo;

import io.swagger.annotations.Api;

/**
 * House endpoint
 * 
 * @author David Acuna
 */
@Api(value="HouseEndpoint", description = "House EndPoint")
@RestController
@RequestMapping("/houses")
public class HouseEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(HouseEndpoint.class);
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private PretenderService pretenderService;	
	
	/**
	 * Endpoint that returns all houses
	 * 
	 * @return houses
	 * 			  list of houses
	 */
	@GetMapping(produces="application/json")
	public ResponseEntity<List<HousePojo>> getHouses() {
		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(houseService.getHouses());
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
	public ResponseEntity<HousePojo> getHouse(@PathVariable("houseId") Long houseId) {
		
		try {
			final HousePojo housePojo = houseService.getHouse(houseId);			
			if(Objects.nonNull(housePojo)) {
				return ResponseEntity.status(HttpStatus.OK).body(housePojo);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
	public ResponseEntity<HousePojo> addHouse(@RequestBody @Valid HouseRequestPojo house) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(houseService.addHouse(house));		
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
	public ResponseEntity<HousePojo> updateHouse(@PathVariable("houseId") Long houseId ,@RequestBody @Valid HouseRequestPojo house) {
		
		try {
			HousePojo k = houseService.updateHouse(houseId, house);
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
	 * Endpoint that deletes a House
	 * 
	 * @param houseId
	 * 			id of the House to delete  	       
	 * @return HttpStatus
	 */
	@DeleteMapping("/{houseId}")
	public ResponseEntity<HttpStatus> deleteHouse(@PathVariable("houseId") Long houseId) {
		
		try {
			HttpStatus s = houseService.deleteHouse(houseId);
			return ResponseEntity.status(s).build();
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
	public ResponseEntity<List<HousePojo>> findHouses(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="sigil", required=false) String sigil, @RequestParam(value="kingdomId", required=false) Long kingdomId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(houseService.findHouses(name, sigil, kingdomId));
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
	public ResponseEntity<List<PretenderPojo>> getHousePretenders(@PathVariable("houseId") Long houseId) {
		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(houseService.getHousePretenders(houseId));
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
	public ResponseEntity<List<PretenderPojo>> addMysteriousPretenders (@RequestBody MultipartFile file,
				@PathVariable("houseId") Long houseId){
		try {
			final HousePojo housePojo = houseService.getHouse(houseId);			
			if(Objects.nonNull(housePojo)) {
				String content = new String(file.getBytes());
				List<String> mysteriousPretenders = Arrays
						.stream(content.split(","))
				        .map(String::trim)
				        .collect(Collectors.toList());
				return ResponseEntity.status(HttpStatus.OK).body(pretenderService.addMysteriousPretenders(mysteriousPretenders, houseId));
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
	public ResponseEntity<HousePojo> patchHouse(@PathVariable("houseId") Long houseId,
				@RequestBody HouseRequestPojo house){
		//This request is handled by CustomizeResponseEntityExceptionHandler
		HousePojo h = houseService.updateHouseField(houseId, house);
		if(Objects.nonNull(h)) {
			return ResponseEntity.status(HttpStatus.OK).body(h);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
