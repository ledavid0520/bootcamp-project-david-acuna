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

import com.globant.project.exceptions.RelatedObjectNotFoundException;
import com.globant.project.model.House;
import com.globant.project.model.Kingdom;
import com.globant.project.model.Pretender;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.HouseRequestPojo;
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.repository.HouseRepository;
import com.globant.project.repository.KingdomRepository;

/**
 * Manage House business logic
 * 
 * @author David Acuna
 */
@Service
public class HouseService {
		
	@Autowired
	private HouseRepository houseRepository;
	
	@Autowired
	private KingdomRepository kingdomRepository;
	
	public HouseService(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}

	/**
	 * Returns all houses
	 * 
	 * @return houses
	 * 			  list of houses
	 */
	public List<HousePojo> getHouses() {
		List<House> houses = houseRepository.findAll();
		return houses.stream()
				.map(h -> HousePojo.getHousePojo(h))
				.collect(Collectors.toList());

	}
	
	/**
	 * Returns a House
	 * 
	 * @param houseId
	 *            house id
	 * @return housePojo
	 * 			  house with id houseId
	 */
	public HousePojo getHouse(Long houseId) {
		return HousePojo.getHousePojo(houseRepository.getById(houseId));
	}

	/**
	 * Creates a House
	 * 
	 * @param house
	 *            house to be added
	 * @return HousePojo
	 * 			  HousePojo with generated id
	 */
	public HousePojo addHouse(@Valid HouseRequestPojo house) {
		Kingdom kingdom = house.getKingdomId() != null ? kingdomRepository.getById(house.getKingdomId()) : null;
		House h = new House(house.getName(), house.getSigil(), kingdom);
		houseRepository.save(h);
		return HousePojo.getHousePojo(h);
	}

	/**
	 * Updates a House
	 * 
	 * @param houseId
	 * 			id of the house to update  
	 * @param house
	 *          house to be updated	       
	 * @return HousePojo
	 * 			updated house
	 */
	public HousePojo updateHouse(Long houseId, @Valid HouseRequestPojo house) {
		House h = houseRepository.getById(houseId);
		if(Objects.nonNull(h)) {
			h.setName(house.getName());
			h.setSigil(house.getSigil());
			Kingdom kingdom = house.getKingdomId() != null ? kingdomRepository.getById(house.getKingdomId()) : null;
			h.setKingdom(kingdom);
			houseRepository.save(h);			
		}
		return HousePojo.getHousePojo(h);
	}

	/**
	 * Deletes a House
	 * 
	 * @param houseId
	 * 			id of the House to delete  	       
	 * @return HttpStatus
	 */
	public HttpStatus deleteHouse(Long houseId) {
		House h = houseRepository.getById(houseId);
		if(Objects.nonNull(h)) {			
			houseRepository.delete(h);
			return HttpStatus.OK;
		}
		return HttpStatus.NOT_FOUND;
	}

	/**
	 * Filters out houses by name and/or sigil and/or kingdomId
	 * 
	 * @param name
	 *            house's name	   
	 * @param sigil
	 *            house's sigil        
	 * @param kingdomId
	 *            house's kingdom
	 * @return houses 
	 * 			  HousePojos that satisfy filters
	 */
	public List<HousePojo> findHouses(String name, String sigil, Long kingdomId) {
		List<House> houses = houseRepository.findAll();

		if (name==null ? false : name.length()>0 ? true : false) {
			houses = houses.stream()
					.filter(h -> h.getName().equals(name))
					.collect(Collectors.toList());
		}
		if (sigil==null ? false : sigil.length()>0 ? true : false) {
			houses = houses.stream()
					.filter(h -> h.getSigil().equals(sigil))
					.collect(Collectors.toList());
		}
		if (kingdomId != null) {
			houses = houses.stream()
					.filter(h -> h.getKingdom().getId().equals(kingdomId))
					.collect(Collectors.toList());
		}
		return houses.stream()
				.map(h -> HousePojo.getHousePojo(h))
				.collect(Collectors.toList());
	}

	/**
	 * Returns Pretenders of a House
	 * 
	 * @param houseId
	 *            house id
	 * @return PretenderPojos
	 * 			  PretenderPojos which id is houseId
	 */
	public List<PretenderPojo> getHousePretenders(Long houseId) {
		List<Pretender> housepretenders = houseRepository.getHousePretenders(houseId);
		
		return housepretenders.stream()
				.map(p -> PretenderPojo.getPretenderPojo(p))
				.collect(Collectors.toList());
	}
	
	/**
	 * Updates specific attributes of a house
	 * 
	 * @param houseId
	 *            house id	            
	 * @param house
	 *            house to be updated
	 * @return HousePojo
	 * 			  updated house
	 */
	public HousePojo updateHouseField(Long houseId, HouseRequestPojo house) {		
		House h = houseRepository.getById(houseId);
		if(Objects.nonNull(h)) {
			String[] ignoredProperties = getNullPropertyNames(house);
			BeanUtils.copyProperties(house, h, ignoredProperties);
			addKingdomToHouse(house.getKingdomId(), h);
			houseRepository.save(h);			
		}
		return HousePojo.getHousePojo(h);
	}
	
	/**
	 * Returns attributes that won't be updated due to
	 * 		they were not sent
	 *             
	 * @param house
	 *            house that contains attributes to be updated
	 * @return string array
	 * 			  Array with attributes that won't be updated
	 */
	private String[] getNullPropertyNames(HouseRequestPojo house) {
	   final BeanWrapper wrappedSource = new BeanWrapperImpl(house);
	   return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
	            .toArray(String[]::new);
	}
	
	/**
	 * Verify the add of a kingdom
	 * 
	 * @param kingdomId
	 *            kingdom id	            
	 * @param house
	 *            house to be updated with the kingdom
	 */
	private void addKingdomToHouse(Long kingdomId, House house) {
		if(kingdomId != null) {
			if(kingdomId > 0) {
				Kingdom k = kingdomRepository.getById(kingdomId);
				if(Objects.nonNull(k)) {
					house.setKingdom(k);
				} else {
					throw new RelatedObjectNotFoundException("Kingdom with id " + kingdomId + " doesn't exists");
				}
			} else {
				house.setKingdom(null);
			}
		}
	}

}
