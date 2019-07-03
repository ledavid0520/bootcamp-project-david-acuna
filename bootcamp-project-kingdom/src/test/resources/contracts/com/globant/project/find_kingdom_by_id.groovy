package com.globant.project

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return kingdom by id=1"

	request {
		url "/kingdoms/1"
		method GET()
	}

	response {
		status OK()
		headers {
			contentType applicationJson()
		}
		body (
			id: 1,
			name: "Winterfell",
			location: "North"
		)
	}
}