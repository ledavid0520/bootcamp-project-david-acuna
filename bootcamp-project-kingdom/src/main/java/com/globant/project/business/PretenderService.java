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

import com.globant.project.enums.LevelEnum;
import com.globant.project.exceptions.RelatedObjectNotFoundException;
import com.globant.project.model.House;
import com.globant.project.model.Pretender;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.pojo.PretenderRequestPojo;
import com.globant.project.repository.HouseRepository;
import com.globant.project.repository.PretenderRepository;

/**
 * Manage Pretender business logic
 * 
 * @author David Acuna
 */
@Service
public class PretenderService {
	
	@Autowired
	private PretenderRepository pretenderRepository;
	
	@Autowired
	private HouseRepository houseRepository;
	
	public PretenderService(PretenderRepository pretenderRepository) {
		this.pretenderRepository = pretenderRepository;
	}
	
	public PretenderService(HouseRepository houseRepository, PretenderRepository pretenderRepository) {
		this.houseRepository = houseRepository;
		this.pretenderRepository = pretenderRepository;
	}
	
	public PretenderService() {
		super();
	}

	/**
	 * Return a pretender with a given house
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
	public HousePojo getPretenderHouse(Long idPretender, Long idHouse) {
		return HousePojo.getHousePojo(pretenderRepository.getPretenderHouse(idPretender, idHouse));		
	}

	/**
	 * Get list of pretenders
	 * 
	 * @return pretenders
	 */
	public List<PretenderPojo> getPretenders() {
		List<Pretender> pretenders = pretenderRepository.findAll();
		return pretenders.stream()
				.map(k -> PretenderPojo.getPretenderPojo(k))
				.collect(Collectors.toList());
	}	

	/**
	 * Returns a pretender
	 * 
	 * @param pretenderId
	 *            pretender id
	 * @return pretenderPojo
	 * 			  pretender with id pretenderId
	 */
	public PretenderPojo getPretender(Long pretenderId) {
		return PretenderPojo.getPretenderPojo(pretenderRepository.getById(pretenderId));
	}

	/**
	 * Creates a Pretender
	 * 
	 * @param pretender
	 *            pretender to be added
	 * @return PretenderPojo
	 * 			  PretenderPojo with generated id
	 */
	public PretenderPojo addPretender(@Valid PretenderRequestPojo pretender) {		
		House house = pretender.getHouseId() != null ? houseRepository.getById(pretender.getHouseId()) : null;
		Pretender p = new Pretender(pretender.getName(), house, LevelEnum.getValueOf(pretender.getLevel()));
		pretenderRepository.save(p);
		return PretenderPojo.getPretenderPojo(p);
	}

	/**
	 * Updates a Pretender
	 * 
	 * @param pretenderId
	 * 			id of the pretender to update  
	 * @param pretender
	 *          pretender to be updated	       
	 * @return PretenderPojo
	 * 			updated pretender
	 */
	public PretenderPojo updatePretender(Long pretenderId, @Valid PretenderRequestPojo pretender) {
		Pretender p = pretenderRepository.getById(pretenderId);
		if(Objects.nonNull(p)) {
			p.setName(pretender.getName());
			House house = pretender.getHouseId() != null ? houseRepository.getById(pretender.getHouseId()) : null;
			p.setHouse(house);
			p.setLevel(LevelEnum.getValueOf(pretender.getLevel()));
			pretenderRepository.save(p);			
		}
		return PretenderPojo.getPretenderPojo(p);
	}

	/**
	 * Deletes a Pretender
	 * 
	 * @param pretenderId
	 * 			id of the Pretender to delete  	       
	 * @return HttpStatus
	 */
	public HttpStatus deletePretender(Long pretenderId) {
		Pretender p = pretenderRepository.getById(pretenderId);
		if(Objects.nonNull(p)) {			
			pretenderRepository.delete(p);
			return HttpStatus.OK;
		}
		return HttpStatus.NOT_FOUND;
	}

	/**
	 * Filters out pretenders by name and/or houseId
	 * 
	 * @param name
	 *            pretender's name	 *            
	 * @param houseId
	 *            pretender's house
	 * @return PretenderPojos
	 * 			  pretenders that satisfy filters
	 */
	public List<PretenderPojo> findPretenders(String name, String level,Long houseId) {
		List<Pretender> pretenders = pretenderRepository.findAll();
		
		if(name==null ? false : name.length()>0 ? true : false) {
			pretenders = pretenders.stream()
						.filter(p -> p.getName().equals(name))
						.collect(Collectors.toList());
		}
		if(houseId !=null) {
			pretenders = pretenders.stream()
						.filter(p -> p.getHouse().getId().equals(houseId))
						.collect(Collectors.toList());
		}
		
		if(level==null ? false : level.length()>0 ? true : false) {			
			pretenders = pretenders.stream()
						.filter(p -> p.getLevel().getLevel().equals(level))
						.collect(Collectors.toList());
		}
		
		return pretenders.stream()
				.map(p -> PretenderPojo.getPretenderPojo(p))
				.collect(Collectors.toList());
	}

	/**
	 * Creates two mysterious pretenders and add them to a House
	 * 
	 * @param mysteriousPretenders
	 * 			  mysterious pretenders to be added, obtained from a file
	 * @param houseId
	 *            house id
	 * @return PretenderPojos
	 * 			  pretenders added to house with houseId 
	 */
	public List<PretenderPojo> addMysteriousPretenders(List<String> mysteriousPretenders, Long houseId) {
		House house = houseRepository.getById(houseId);
		return mysteriousPretenders.stream()
					.map(p -> pretenderRepository.save(new Pretender(p, house, LevelEnum.PLATINUM)))
					.map(np -> PretenderPojo.getPretenderPojo(np))
					.collect(Collectors.toList());

	}

	/**
	 * Updates specific attributes of a pretender
	 * 
	 * @param pretenderId
	 *            pretender id	            
	 * @param pretender
	 *            pretender to be updated
	 * @return PretenderPojo
	 * 			  updated pretender
	 */
	public PretenderPojo updatePretenderField(Long pretenderId, PretenderRequestPojo pretender) {
		Pretender p = pretenderRepository.getById(pretenderId);
		if(Objects.nonNull(p)) {
			String[] ignoredProperties = getNullPropertyNames(pretender);
			BeanUtils.copyProperties(pretender, p, ignoredProperties);
			addHouseToPretender(pretender.getHouseId(), p);
			addLevelEnum(pretender.getLevel(), p);
			pretenderRepository.save(p);
		}
		return PretenderPojo.getPretenderPojo(p);
	}

	/**
	 * Returns attributes that won't be updated due to
	 * 		they were not sent
	 *             
	 * @param pretender
	 *            pretender that contains attributes to be updated
	 * @return string array
	 * 			  Array with attributes that won't be updated
	 */
	private String[] getNullPropertyNames(PretenderRequestPojo pretender) {
	   final BeanWrapper wrappedSource = new BeanWrapperImpl(pretender);
	   return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
	            .toArray(String[]::new);
	}
	
	/**
	 * Verify the add of a house
	 * 
	 * @param houseId
	 *            house id	            
	 * @param pretender
	 *            pretender to be updated with the house
	 */
	private void addHouseToPretender(Long houseId, Pretender pretender) {
		if(houseId != null) {
			if(houseId > 0) {
				House h = houseRepository.getById(houseId);
				if(Objects.nonNull(h)) {
					pretender.setHouse(h);
				}else {
					throw new RelatedObjectNotFoundException("House with id " + houseId + " doesn't exists");
				}
			}else {
				pretender.setHouse(null);
			}
		}
	}
	
	private void addLevelEnum(String level, Pretender pretender) {
		if(level != null) {
			pretender.setLevel(LevelEnum.getValueOf(level));						
		}	
	}

}
