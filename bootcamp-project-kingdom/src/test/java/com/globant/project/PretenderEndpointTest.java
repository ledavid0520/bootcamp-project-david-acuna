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

import com.globant.project.business.PretenderService;
import com.globant.project.enums.LevelEnum;
import com.globant.project.model.House;
import com.globant.project.model.Pretender;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.PretenderPojo;
import com.globant.project.pojo.PretenderRequestPojo;
import com.globant.project.repository.PretenderRepository;

public class PretenderEndpointTest {

	@Mock
	private PretenderRepository pretenderRepository;

	PretenderService pretenderService;

	private Long pretenderId;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		pretenderId = 1L;
		pretenderService = new PretenderService(pretenderRepository);
//		when(pretenderRepository.getById(pretenderId)).thenReturn(new Pretender(pretenderId, "Jon Snow", any(House.class)));
	}
	
	@Test
	public void getPretenderHouseTest() {	
		House house = new House(1L, "Stark", "Grey Direwolf", null) ;
		
		when(pretenderRepository.getPretenderHouse(pretenderId, 1L)).thenReturn(house);
		
		HousePojo result = pretenderService.getPretenderHouse(pretenderId, 1L);	
		
		Assert.assertEquals(result.getName(), "Stark");
		Assert.assertEquals(result.getSigil(), "Grey Direwolf");
		Assert.assertTrue(result.getId()>0);
	}

	@Test
	public void getPretendersTest() {
		List<Pretender> pretenders = new ArrayList<>();
		pretenders.add(new Pretender(pretenderId, "Jon Snow", null, LevelEnum.PLATINUM));
	
		when(pretenderRepository.findAll()).thenReturn(pretenders);
		
		List<PretenderPojo> result = pretenderService.getPretenders();		
		Assert.assertEquals(result.get(0).getName(),"Jon Snow");
		Assert.assertEquals(result.size(), 1);
		Assert.assertNotNull(result);
	}

	@Test
	public void getPretenderTest() {		
		when(pretenderRepository.getById(pretenderId)).thenReturn(new Pretender(pretenderId, "Jon Snow", any(House.class), LevelEnum.PLATINUM));
	
		PretenderPojo result = pretenderService.getPretender(pretenderId);	
		Assert.assertEquals(result.getName(), "Jon Snow");
		Assert.assertTrue(result.getId()>0);
	}

	@Test
	public void addPretenderTest() {
		Pretender p = new Pretender(2L, "Jaime Lannister", null, LevelEnum.GOLD);	
	
		when(pretenderRepository.save(any(Pretender.class))).thenReturn(p);

		PretenderPojo result = pretenderService.addPretender(new PretenderRequestPojo(p));
		Assert.assertEquals(result.getName(), "Jaime Lannister");
		Assert.assertNotNull(result);
	}

	@Test
	public void updatePretenderTest() {			
		Pretender p = new Pretender(pretenderId, "Jon Snow!!", null, LevelEnum.PLATINUM);		
		
		when(pretenderRepository.getById(pretenderId)).thenReturn(new Pretender(pretenderId, "Jon Snow", any(House.class), LevelEnum.PLATINUM));

		PretenderPojo result = pretenderService.updatePretender(pretenderId, new PretenderRequestPojo(p));
		Assert.assertEquals(result.getName(), "Jon Snow!!");
		Assert.assertEquals(result.getId(),pretenderId);
	}
	
	@Test
	public void deletePretenderTest() {			
		when(pretenderRepository.getById(pretenderId)).thenReturn(new Pretender(pretenderId, "Jon Snow", any(House.class), LevelEnum.PLATINUM));
		
		HttpStatus result = pretenderService.deletePretender(pretenderId);	
		Assert.assertEquals(result, HttpStatus.OK);
	}
	
	@Test
	public void findPretendersTest() {		
		List<Pretender> pretenders = new ArrayList<>();
		pretenders.add(new Pretender(pretenderId, "Jon Snow", null, LevelEnum.PLATINUM));
		
		when(pretenderRepository.findAll()).thenReturn(pretenders);
		
		List<PretenderPojo> result = pretenderService.findPretenders("Jon Snow", null, null);	
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 1);
		Assert.assertEquals(result.get(0).getName(), "Jon Snow");
	}
	
	@Test
	public void patchPretenderTest() {	
		when(pretenderRepository.getById(pretenderId)).thenReturn(new Pretender(pretenderId, "Jon Snow", any(House.class), LevelEnum.PLATINUM));
		
		Pretender p = new Pretender(pretenderId, "Jonsito Snow", null, LevelEnum.PLATINUM);			

		PretenderPojo result = pretenderService.updatePretenderField(pretenderId, new PretenderRequestPojo(p));
		Assert.assertEquals(result.getName(), "Jonsito Snow");
		Assert.assertEquals(result.getId(),pretenderId);
		Assert.assertNotNull(result);
	}

}
