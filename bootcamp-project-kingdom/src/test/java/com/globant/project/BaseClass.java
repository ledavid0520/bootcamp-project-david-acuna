package com.globant.project;

import com.globant.project.business.KingdomService;
import com.globant.project.endpoint.KingdomEndpoint;
import com.globant.project.model.Kingdom;
import com.globant.project.pojo.KingdomPojo;
import com.globant.project.pojo.KingdomRequestPojo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.any;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * To verify an API provider (the Spring controller in our case), Spring Cloud Contract automatically generates JUnit
 * tests from a given contract. In order to give these automatically generated tests a working context, we need to
 * create a base test class which is subclassed by all generated tests:
 *
 * In this base class, we’re setting up a Spring Boot application with @SpringBootTest and are mocking away the
 * CustomerService so that it always returns the person specified in the contract. Then, we set up RestAssured so that the
 * generated tests can simply use RestAssured to send requests against our controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootKingdomApplication.class)
public abstract class BaseClass {

	@Autowired
	private KingdomEndpoint kingdomEndpoint;

	@MockBean
	private KingdomService kingdomService;

	@Before public void setup() {
		RestAssuredMockMvc.standaloneSetup(kingdomEndpoint);

		Mockito.when(kingdomService.getKingdom(1L))
				.thenReturn(KingdomPojo.getKingdomPojo(new Kingdom(1L, "Winterfell", "North")));
		
		Mockito.when(kingdomService.addKingdom(any(KingdomRequestPojo.class)))
				.thenReturn(KingdomPojo.getKingdomPojo(new Kingdom(3L, "Bear Island", "North")));
		
		Mockito.when(kingdomService.updateKingdom(Mockito.eq(2L), any(KingdomRequestPojo.class)))
				.thenReturn(KingdomPojo.getKingdomPojo(new Kingdom(2L, "Casterly Rock", "Westeros")));
		
		Mockito.when(kingdomService.deleteKingdom(3L))
				.thenReturn(HttpStatus.OK);
		
	}

}
