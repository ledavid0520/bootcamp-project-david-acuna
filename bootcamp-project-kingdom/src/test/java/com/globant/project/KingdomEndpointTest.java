package com.globant.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.globant.project.business.KingdomService;
import com.globant.project.model.House;
import com.globant.project.model.Kingdom;
import com.globant.project.pojo.HousePojo;
import com.globant.project.pojo.KingdomPojo;
import com.globant.project.pojo.KingdomRequestPojo;
import com.globant.project.repository.KingdomRepository;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootKingdomApplication.class)
@AutoConfigureMockMvc
public class KingdomEndpointTest {
	
	@Mock
    private KingdomRepository kingdomRepository;
	
	private KingdomService kingdomService;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	private Long kingdomId; 
	
	@Before
    public void setUp() throws Exception {	
        MockitoAnnotations.initMocks(this);
        kingdomId = 1L;
        kingdomService = new KingdomService(kingdomRepository);
        when(kingdomRepository.getById(kingdomId)).thenReturn(new Kingdom(kingdomId, "The North","North"));
    }
	
	@Test
	@WithMockUser(value="admin",roles="ADMIN")
	public void securityGetKingdomsTest() throws Exception{		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		MockHttpServletRequestBuilder request = get("/kingdoms");
		
		this.mvc.perform(request).andExpect(status().isOk()).andReturn();		
	}
	
	@Test
	public void shouldFaildsecurityGetKingdomsTest() throws Exception{		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		MockHttpServletRequestBuilder request = get("/kingdoms");
		
		this.mvc.perform(request).andExpect(status().isUnauthorized()).andReturn();		
	}
	
	@Test
	public void findKingdomsTest() {		
		List<Kingdom> kingdoms = new ArrayList<>();
		kingdoms.add(new Kingdom(kingdomId, "The North","North"));
		kingdoms.add(new Kingdom(kingdomId+1, "Bear Island","North"));
		
		when(kingdomRepository.findAll()).thenReturn(kingdoms);
		
		List<KingdomPojo> result = kingdomService.findKingdoms(null,"North");	
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 2);
		
		Assert.assertEquals(result.get(0).getName(), "The North");
		Assert.assertEquals(result.get(0).getLocation(), "North");
		Assert.assertTrue(result.get(0).getId()>0);
		
		Assert.assertEquals(result.get(1).getName(), "Bear Island");
		Assert.assertEquals(result.get(1).getLocation(), "North");
		Assert.assertTrue(result.get(1).getId()>0);
	}
	
	@Test
	public void getKingdomHousesTest() {	
		List<House> houses = new ArrayList<>();
		houses.add(new House (1L, "Stark", "Grey Direwolf", new Kingdom(kingdomId, "The North","North")));
		
		when(kingdomRepository.getKingdomHouses(kingdomId)).thenReturn(houses);
		
		List<HousePojo> result = kingdomService.getKingdomHouses(kingdomId);	
		Assert.assertEquals(result.size(), 1);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.get(0).getId(), kingdomId);
		Assert.assertEquals(result.get(0).getName(), "Stark");
		Assert.assertEquals(result.get(0).getSigil(), "Grey Direwolf");
	}
	
	@Test
	public void getKingdomsTest() {
		List<Kingdom> kingdoms = new ArrayList<>();
		kingdoms.add(new Kingdom(kingdomId, "The North","North"));
		
		when(kingdomRepository.findAll()).thenReturn(kingdoms);
		
		List<KingdomPojo> result = kingdomService.getKingdoms();	
		Assert.assertEquals(result.size(), 1);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.get(0).getId(), kingdomId);
		Assert.assertEquals(result.get(0).getName(), "The North");
		Assert.assertEquals(result.get(0).getLocation(), "North");
	}
	
	@Test
	public void getKingdomTest() {		
		KingdomPojo result = kingdomService.getKingdom(kingdomId);	
		Assert.assertEquals(result.getId(), kingdomId);
		Assert.assertEquals(result.getName(), "The North");
		Assert.assertEquals(result.getLocation(), "North");
		Assert.assertTrue(result.getId()>0);
	}
	
	@Test
	public void addKingdomTest() {		
		Long newKingdomId = 2L;
		Kingdom k = new Kingdom(newKingdomId, "The Westerlands","West");
		
		when(kingdomRepository.save(any(Kingdom.class))).thenReturn(k);
		
		KingdomPojo result = kingdomService.addKingdom(new KingdomRequestPojo(k));	
		Assert.assertEquals(result.getName(), "The Westerlands");
		Assert.assertEquals(result.getLocation(), "West");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void updateKingdomTest() {		
		Kingdom k = new Kingdom(kingdomId, "The North!!","North!!");
		
		when(kingdomRepository.save(any(Kingdom.class))).thenReturn(k);
		
		KingdomPojo result = kingdomService.updateKingdom(kingdomId, new KingdomRequestPojo(k));	
		Assert.assertEquals(result.getName(), "The North!!");
		Assert.assertEquals(result.getLocation(), "North!!");
		Assert.assertEquals(result.getId(),kingdomId);
	}
	
	@Test	
	public void deleteKingdomTest()  {			
		HttpStatus result = kingdomService.deleteKingdom(kingdomId);	
		Assert.assertEquals(result, HttpStatus.OK);		
	}	
	
	@Test
	public void patchKingdomTest() {	
		Kingdom k = new Kingdom(kingdomId, "The North2", null);	

		KingdomPojo result = kingdomService.updateKingdomField(kingdomId, new KingdomRequestPojo(k));
		Assert.assertEquals(result.getName(), "The North2");
		Assert.assertEquals(result.getLocation(), "North");
		Assert.assertEquals(result.getId(),kingdomId);
		Assert.assertNotNull(result);
	}

}
