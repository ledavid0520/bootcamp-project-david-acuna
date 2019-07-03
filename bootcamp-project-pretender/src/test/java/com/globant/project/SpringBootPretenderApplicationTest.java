package com.globant.project;

import java.net.URI;
import java.net.URISyntaxException;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.globant.project.model.Kingdom;
import com.globant.project.pojo.KingdomPojo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.globant.bootcamp:bootcamp-project-kingdom")
public class SpringBootPretenderApplicationTest {
	
	@StubRunnerPort("bootcamp-project-kingdom")
	private int port;
	
//	@Rule
//	public StubRunnerRule stubRunnerRule = new StubRunnerRule()
//		.downloadStub("com.globant.bootcamp", "bootcamp-project-kingdom", "0.0.1-SNAPSHOT", "stubs")
//		.withPort(8100)
//		.stubsMode(StubRunnerProperties.StubsMode.LOCAL);
	
	@Test
	public void get_kingdom_from_kingdom_contract() {
		// given:
		RestTemplate restTemplate = new RestTemplate();
		
		// when:
		ResponseEntity<KingdomPojo> entity = 
				restTemplate.getForEntity("http://localhost:"+port+"/kingdoms/1", KingdomPojo.class);

		// then:
		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(200);
		BDDAssertions.then(entity.getBody().getId()).isEqualTo(1l);
		BDDAssertions.then(entity.getBody().getName()).isEqualTo("Winterfell");
		BDDAssertions.then(entity.getBody().getLocation()).isEqualTo("North");
		
	}
	
	@Test
	public void create_customer_from_service_contract() {
		// given:
		RestTemplate restTemplate = new RestTemplate();
		
		// when:
		KingdomPojo body = KingdomPojo.getKingdomPojo(new Kingdom(3L, "Bear Island", "North"));
		
		ResponseEntity<KingdomPojo> entity = 
				restTemplate.postForEntity("http://localhost:"+port+"/kingdoms", body, KingdomPojo.class);

		// then:
		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(201);
		BDDAssertions.then(entity.getBody().getId()).isEqualTo(3l);
		BDDAssertions.then(entity.getBody().getName()).isEqualTo("Bear Island");
		BDDAssertions.then(entity.getBody().getLocation()).isEqualTo("North");
		
	}
	
	@Test
	public void delete_customer_from_service_contract() throws URISyntaxException {
		// given:
		RestTemplate restTemplate = new RestTemplate();
		
		// when:
		Long id = 3L;
		String url = "http://localhost:"+port+"/kingdoms/"+id;		
		HttpHeaders headers = new HttpHeaders();

		RequestEntity<?>  requestEntity = 
				new RequestEntity<>(null, headers,HttpMethod.DELETE, new URI(url));

		ResponseEntity<?> entity = 
				restTemplate.exchange(requestEntity, Kingdom.class);
		// then:
		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(200);		
	}
	
	@Test
	public void update_customer_from_service_contract() throws URISyntaxException {
		// given:
		RestTemplate restTemplate = new RestTemplate();
		
		// when:
		Long id = 2L;
		String url = "http://localhost:"+port+"/kingdoms/"+id;	
		KingdomPojo body = KingdomPojo.getKingdomPojo(new Kingdom(2L, "Casterly Rock", "Westeros"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","application/json");
		
		RequestEntity<?>  requestEntity = 
				new RequestEntity<>(body, headers,HttpMethod.PUT, new URI(url));

		ResponseEntity<KingdomPojo> entity = 
				restTemplate.exchange(requestEntity, KingdomPojo.class);
		// then:
		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(200);
		BDDAssertions.then(entity.getBody().getId()).isEqualTo(2l);
		BDDAssertions.then(entity.getBody().getName()).isEqualTo("Casterly Rock");
		BDDAssertions.then(entity.getBody().getLocation()).isEqualTo("Westeros");
	}

}
