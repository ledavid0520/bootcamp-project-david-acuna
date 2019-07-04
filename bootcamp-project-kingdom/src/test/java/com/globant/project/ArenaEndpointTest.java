package com.globant.project;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.globant.project.business.ArenaService;
import com.globant.project.model.Arena;
import com.globant.project.pojo.ResultArenaPojo;
import com.globant.project.repository.ResultRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootKingdomApplication.class)
@AutoConfigureMockMvc
public class ArenaEndpointTest {
	
	@Mock
    private ResultRepository resultRepository;
	
	private ArenaService arenaService;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
    public void setUp() throws Exception {		
		MockitoAnnotations.initMocks(this);
		arenaService = new ArenaService(resultRepository);
	}
	
	@Test
	@WithMockUser(value="david",roles="USER")
	public void getArenaResultsTest() throws Exception{	
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		MockHttpServletRequestBuilder request = get("/arena");
		
		this.mvc.perform(request).andExpect(status().isOk()).andReturn();			
	}
	
	@Test
	@WithMockUser(value="david",roles="USER")
	public void playArenaTest() throws Exception{	
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		MockHttpServletRequestBuilder request = get("/arena/movement");
		
		this.mvc.perform(request).andExpect(status().isOk()).andReturn();			
	}
	
	@Test
	@WithMockUser(value="admin",roles="ADMIN")
	public void endArenaTest() throws Exception{	
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		MockHttpServletRequestBuilder request = get("/arena/endArena");
		
		this.mvc.perform(request).andExpect(status().isOk()).andReturn();			
	}
	
	@Test
	@WithMockUser(value="david",roles="USER")
	public void endArenaNotAdminTest() throws Exception{	
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		MockHttpServletRequestBuilder request = get("/arena/endArena");
		
		this.mvc.perform(request).andExpect(status().isForbidden()).andReturn();			
	}

	@Test
	public void getArenaResultsContentTest() throws Exception{
		List<ResultArenaPojo> result = arenaService.getResults(new Arena());
		
		Assert.assertEquals(result.size(), 0);
		Assert.assertNotNull(result);		
	}
	
}
