package com.globant.project.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

public class HeaderUtils {
	
	public static void obtainHeaders(HttpHeaders headers, HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				headers.add("Authorization", request.getHeader(headerNames.nextElement()));
			}
		}
	}

}
