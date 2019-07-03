package com.globant.project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user")
				.password("{noop}user")
				.roles("USER")
			.and().withUser("david")
				.password("{noop}david").
				roles("USER")
			.and().withUser("admin")
				.password("{noop}admin").
				roles("ADMIN");
	}

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                	.antMatchers(HttpMethod.GET, "/kingdoms")
                		.hasRole("ADMIN")    
            		.antMatchers(HttpMethod.PATCH)
            			.permitAll()	
            		.anyRequest()
            			.authenticated()               
                .and()
                .httpBasic();        
        httpSecurity
        	.csrf()
        	.disable();
        
        //Use of h2 console
        httpSecurity.
        	headers()
        	.frameOptions()
        	.disable();
    }
	
}
