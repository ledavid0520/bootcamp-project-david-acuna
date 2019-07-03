package com.globant.project.interceptor;


import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.globant.project.exception.NoAuthorizationException;

@Component
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(Objects.isNull(request.getHeader("Authorization"))){
			throw new NoAuthorizationException("You don't have credentials");
		}
		return super.preHandle(request, response, handler);
    }

}
