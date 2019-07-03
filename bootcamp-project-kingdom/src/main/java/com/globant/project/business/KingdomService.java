package com.globant.project.business;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.globant.project.model.House;
import com.globant.project.model.Kingdom;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.KingdomPojo;
import com.globant.project.pojo.KingdomRequestPojo;
import com.globant.project.repository.KingdomRepository;

/**
 * Manage Kingdom business logic
 * 
 * @author David Acuna
 */
@Service
public class KingdomService {
	
	@Autowired
	private KingdomRepository kingdomRepository;
	
	public KingdomService(KingdomRepository kingdomRepository) {
		this.kingdomRepository = kingdomRepository;
	}

	/**
	 * Get list of kingdoms
	 * 
	 * @return kingdoms
	 */
	public List<KingdomPojo> getKingdoms() {
		List<Kingdom> kingdoms = kingdomRepository.findAll();
		return kingdoms.stream()
				.map(k -> KingdomPojo.getKingdomPojo(k))
				.collect(Collectors.toList());
	}

	/**
	 * Get a kingdom
	 * 
	 * @param id 
	 * 		kingdom id
	 * @return kingdom
	 */
	public KingdomPojo getKingdom(Long id) {
		return KingdomPojo.getKingdomPojo(kingdomRepository.getById(id));
	}

	/**
	 * Add a kingdom
	 * 
	 * @param k 
	 * 		kingdom to be added
	 * @return Added kingdom with generated id
	 */
	public KingdomPojo addKingdom(@Valid KingdomRequestPojo k) {
		Kingdom kingdom = new Kingdom(k.getName(), k.getLocation());
		kingdomRepository.save(kingdom);
		return KingdomPojo.getKingdomPojo(kingdom);
	}

	/**
	 * Updates a Kingdom
	 * 
	 * @param id
	 * 			id of the kingdom to update  
	 * @param kingdom
	 *          kingdom to be updated	       
	 * @return kingdom
	 * 			updated kingdom
	 */
	public KingdomPojo updateKingdom(Long id, @Valid KingdomRequestPojo kingdom) {
		Kingdom k = kingdomRepository.getById(id);
		if(Objects.nonNull(k)) {
			k.setName(kingdom.getName());
			k.setLocation(kingdom.getLocation());
			kingdomRepository.save(k);			 
		}
		return KingdomPojo.getKingdomPojo(k);
	}

	/**
	 * Deletes a Kingdom
	 * 
	 * @param id
	 * 			id of the kingdom to delete  	       
	 * @return HttpStatus
	 */
	public HttpStatus deleteKingdom(Long id) {
		Kingdom k = kingdomRepository.getById(id);
		if(Objects.nonNull(k)) {			
			kingdomRepository.delete(k);
			return HttpStatus.OK;
		}
		return HttpStatus.NOT_FOUND;
	}

	/**
	 * Returns Houses of a Kingdom
	 * 
	 * @param kingdomId
	 *            kingdom id
	 * @return kingdomHouses
	 * 			  HousePojos which id is kingdomId
	 */
	public List<HousePojo> getKingdomHouses(Long id) {
		List<House> kingdomHouses = kingdomRepository.getKingdomHouses(id);		
		return kingdomHouses.stream()
				.map(k -> HousePojo.getHousePojo(k))
				.collect(Collectors.toList());	
	}

	/**
	 * Filters out kingdoms by name and/or location
	 * 
	 * @param name
	 *            kingdom's name	 *            
	 * @param location
	 *            kingdom's location
	 * @return KingdomPojos
	 * 			  kingdoms that satisfy filters
	 */
	public List<KingdomPojo> findKingdoms(String name, String location) {
		List<Kingdom> kingdoms = kingdomRepository.findAll();
		
		if(name==null ? false : name.length()>0 ? true : false) {
			kingdoms = kingdoms.stream()
						.filter(k -> k.getName().equals(name))
						.collect(Collectors.toList());
		}
		
		if(location==null ? false : location.length()>0 ? true : false) {
			kingdoms = kingdoms.stream()
						.filter(k -> k.getLocation().equals(location))
						.collect(Collectors.toList());
		}
		return kingdoms.stream()
				.map(k -> KingdomPojo.getKingdomPojo(k))
				.collect(Collectors.toList());
	}

	/**
	 * Updates specific attributes of a kingdom
	 * 
	 * @param kingdomId
	 *            kingdom id	            
	 * @param kingdom
	 *            kingdom to be updated
	 * @return KingdomPojo
	 * 			  updated kingdom
	 */
	public KingdomPojo updateKingdomField(Long kingdomId, KingdomRequestPojo kingdom) {
		Kingdom k = kingdomRepository.getById(kingdomId);
		if(Objects.nonNull(k)) {
			String[] ignoredProperties = getNullPropertyNames(kingdom);
			BeanUtils.copyProperties(kingdom, k, ignoredProperties);
			kingdomRepository.save(k);
		}
		return KingdomPojo.getKingdomPojo(k);
	}
	
	/**
	 * Returns attributes that won't be updated due to
	 * 		they were not sent
	 *             
	 * @param kingdom
	 *            kingdom that contains attributes to be updated
	 * @return string array
	 * 			  Array with attributes that won't be updated
	 */
	private String[] getNullPropertyNames(KingdomRequestPojo kingdom) {
	   final BeanWrapper wrappedSource = new BeanWrapperImpl(kingdom);
	   return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
	            .toArray(String[]::new);
	}
	
}
