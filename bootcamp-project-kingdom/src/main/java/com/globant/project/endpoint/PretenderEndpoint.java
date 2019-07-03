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

import com.globant.project.business.PretenderService;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.pojo.PretenderRequestPojo;

import io.swagger.annotations.Api;

/**
 * Pretender endpoint
 * 
 * @author David Acuna
 */
@Api(value="PretenderEndpoint", description = "Pretender EndPoint")
@RestController
@RequestMapping("/pretenders")
public class PretenderEndpoint {
	
	private static final Logger log = LoggerFactory.getLogger(PretenderEndpoint.class);
	
	@Autowired
	private PretenderService pretenderService;	
	
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
	public ResponseEntity<HousePojo> getPretenderHouse(@PathVariable("idPretender") Long idPretender,
				@PathVariable("idHouse") Long idHouse) {		
		try {		
			HousePojo pretender_house = pretenderService.getPretenderHouse(idPretender, idHouse);
			if(Objects.nonNull(pretender_house)) {
				return ResponseEntity.status(HttpStatus.OK).body(pretender_house);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
	public ResponseEntity<List<PretenderPojo>> getPretenders() {
		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(pretenderService.getPretenders());
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
	public ResponseEntity<PretenderPojo> getPretender(@PathVariable("pretenderId") Long pretenderId) {
		
		try {
			final PretenderPojo pretenderPojo = pretenderService.getPretender(pretenderId);			
			if(Objects.nonNull(pretenderPojo)) {
				return ResponseEntity.status(HttpStatus.OK).body(pretenderPojo);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
	public ResponseEntity<PretenderPojo> addPretender(@RequestBody @Valid PretenderRequestPojo pretender) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(pretenderService.addPretender(pretender));		
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
	public ResponseEntity<PretenderPojo> updatePretender(@PathVariable("pretenderId") Long pretenderId ,@RequestBody @Valid PretenderRequestPojo pretender) {
		
		try {
			PretenderPojo k = pretenderService.updatePretender(pretenderId, pretender);
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
	 * Endpoint that deletes a Pretender
	 * 
	 * @param pretenderId
	 * 			id of the Pretender to delete  	       
	 * @return HttpStatus
	 */
	@DeleteMapping("/{pretenderId}")
	public ResponseEntity<HttpStatus> deletePretender(@PathVariable("pretenderId") Long pretenderId) {
		
		try {
			HttpStatus s = pretenderService.deletePretender(pretenderId);
			return ResponseEntity.status(s).build();
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
	public ResponseEntity<List<PretenderPojo>> findPretenders(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="level", required=false) String level, @RequestParam(value="houseId", required=false) Long houseId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(pretenderService.findPretenders(name, level, houseId));
		} catch (Exception e) {
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
	public ResponseEntity<PretenderPojo> patchPretender(@PathVariable("pretenderId") Long pretenderId,
				@RequestBody PretenderRequestPojo pretender){
		//This request is handled by CustomizeResponseEntityExceptionHandler
		PretenderPojo p = pretenderService.updatePretenderField(pretenderId, pretender);
		if(Objects.nonNull(p)) {
			return ResponseEntity.status(HttpStatus.OK).body(p);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		
	}
	
}
