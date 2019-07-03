package com.globant.project

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should update kingdom with id=2"

	request {
		url "/kingdoms/2"
		method PUT()
		headers {
			contentType applicationJson()
		}
		body (
			id: 2,
			name: "Casterly Rock",
			location: "Westeros"
		)
	}

	response {
		status OK()
		headers {
			contentType applicationJson()
		}
		body (
			id: 2,
			name: "Casterly Rock",
			location: "Westeros"
		)
	}
}