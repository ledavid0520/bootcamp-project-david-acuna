package com.globant.project.configuration;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class ActuatorConfiguration extends AbstractHealthIndicator {
	
	@Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        
        builder.up()
                .withDetail("app", "Project Running... :D")
                .withDetail("error", "Of course not ;)");
    }

}
