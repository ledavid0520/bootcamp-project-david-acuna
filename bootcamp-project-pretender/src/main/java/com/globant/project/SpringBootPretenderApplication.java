package com.globant.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootPretenderApplication {	
	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "production");
		SpringApplication.run(SpringBootPretenderApplication.class, args);
	}
}
