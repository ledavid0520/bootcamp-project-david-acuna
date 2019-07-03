package com.globant.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.globant.project.business.HouseService;
import com.globant.project.business.PretenderService;
import com.globant.project.enums.LevelEnum;
import com.globant.project.model.House;
import com.globant.project.model.Pretender;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.HouseRequestPojo;
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.repository.HouseRepository;
import com.globant.project.repository.PretenderRepository;

public class HouseEndpointTest {

	@Mock
	private HouseRepository houseRepository;
	
	@Mock
	private PretenderRepository pretenderRepository;

	HouseService houseService;

	private Long houseId;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		houseId = 1L;
		houseService = new HouseService(houseRepository);
		when(houseRepository.getById(houseId)).thenReturn(new House(houseId, "Stark", "Grey Direwolf", null));
	}
	
	//Use of both houseRepository and pretenderRepository because the method use them 
	@Test
	public void addMysteriousPretendersTest() {		
		PretenderService pretenderServiceHouseRepository = new PretenderService(houseRepository, pretenderRepository);
		
		Pretender p = new Pretender(1L, "Drogon", null, LevelEnum.PLATINUM);
		List<String> newPretenderNames = new ArrayList<>();
		newPretenderNames.add("Drogon");
					
		when(pretenderRepository.save(any(Pretender.class))).thenReturn(p);
		
		List<PretenderPojo> result = pretenderServiceHouseRepository.addMysteriousPretenders(newPretenderNames, houseId);	
		Assert.assertEquals(result.size(), 1);
		Assert.assertEquals(result.get(0).getName(), "Drogon");
		Assert.assertNotNull(result);
	}

	@Test
	public void getHousesTest() {
		List<House> houses = new ArrayList<>();
		houses.add(new House(houseId, "Stark", "Grey Direwolf", null));
		
		when(houseRepository.findAll()).thenReturn(houses);
		
		List<HousePojo> result = houseService.getHouses();		
		Assert.assertEquals(result.size(), 1);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.get(0).getName(), "Stark");
		Assert.assertEquals(result.get(0).getSigil(), "Grey Direwolf");
	}

	@Test
	public void getHouseTest() {		
		HousePojo result = houseService.getHouse(houseId);	
		
		Assert.assertEquals(result.getName(), "Stark");
		Assert.assertEquals(result.getSigil(), "Grey Direwolf");
		Assert.assertTrue(result.getId()>0);
	}

	@Test
	public void addHouseTest() {
		House h = new House(2L, "Bolton", "Flayed Man", null);	
		
		when(houseRepository.save(any(House.class))).thenReturn(h);

		HousePojo result = houseService.addHouse(new HouseRequestPojo(h));
		Assert.assertEquals(result.getName(), "Bolton");
		Assert.assertEquals(result.getSigil(), "Flayed Man");
		Assert.assertNotNull(result);
	}

	@Test
	public void updateHouseTest() {	
		House h = new House(houseId, "Stark!!", "Grey Direwolf!!", null);		

		HousePojo result = houseService.updateHouse(houseId, new HouseRequestPojo(h));
		Assert.assertEquals(result.getName(), "Stark!!");
		Assert.assertEquals(result.getId(),houseId);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void deleteHouseTest() {			
		HttpStatus result = houseService.deleteHouse(houseId);	
		Assert.assertEquals(result, HttpStatus.OK);
	}
	
	@Test
	public void findHousesTest() {		
		List<House> houses = new ArrayList<>();
		houses.add(new House(houseId, "Stark", "Grey Direwolf", null));
		
		when(houseRepository.findAll()).thenReturn(houses);
		
		List<HousePojo> result = houseService.findHouses("Stark", null, null);	
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 1);
		Assert.assertEquals(result.get(0).getName(), "Stark");
		Assert.assertEquals(result.get(0).getSigil(), "Grey Direwolf");
	}	
	
	@Test
	public void getHousePretendersTest() {	
		List<Pretender> pretenders = new ArrayList<>();
		pretenders.add(new Pretender(1L, "Jon Snow", null, LevelEnum.PLATINUM));
		
		when(houseRepository.getHousePretenders(houseId)).thenReturn(pretenders);
		
		List<PretenderPojo> result = houseService.getHousePretenders(houseId);	
		Assert.assertEquals(result.size(), 1);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.get(0).getName(),"Jon Snow");
	}
	
	@Test
	public void patchHouseTest() {	
		House h = new House(houseId, "Stark2", null, null);	

		HousePojo result = houseService.updateHouseField(houseId, new HouseRequestPojo(h));
		Assert.assertEquals(result.getName(), "Stark2");
		Assert.assertEquals(result.getSigil(), "Grey Direwolf");
		Assert.assertEquals(result.getId(),houseId);
		Assert.assertNotNull(result);
	}

}
