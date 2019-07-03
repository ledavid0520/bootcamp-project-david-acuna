package com.globant.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.globant.project.model.Arena;

@Configuration
public class BeanConfiguration {
	
	@Bean
    @Scope("singleton")
    @Autowired 
    public Arena arenaSingleton() {
        return new Arena();
    }

}
