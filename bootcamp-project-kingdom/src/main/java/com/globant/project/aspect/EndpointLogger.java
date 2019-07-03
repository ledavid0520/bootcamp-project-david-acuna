package com.globant.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EndpointLogger {
	
	private static final Logger log = LoggerFactory.getLogger(EndpointLogger.class);
	
	@Pointcut("within(com.globant.project.endpoint..*)")
	public void endpointsPointcut() {}
	
	@Before("endpointsPointcut()")
    public void before(JoinPoint joinPoint) throws Throwable {
		log.info("Request IN for: " + joinPoint.getSignature().getName() + "(..) of "
				+ joinPoint.getSignature().getDeclaringTypeName());
    }
	
	@After("endpointsPointcut()")
    public void after(JoinPoint joinPoint) throws Throwable {
		log.info("Request OUT for: " + joinPoint.getSignature().getName() + "(..) of "
				+ joinPoint.getSignature().getDeclaringTypeName());
    }

}
