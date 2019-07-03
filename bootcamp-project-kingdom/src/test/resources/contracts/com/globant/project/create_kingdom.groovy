package com.globant.project

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should create kingdom with id=3"

	request {
		url "/kingdoms"
		method POST()
		headers {
			contentType applicationJson()
		}
		body (
			id: 3,
			name: "Bear Island",
			location: "North"
		)
	}

	response {
		status CREATED()
		headers {
			contentType applicationJson()
		}
		body (
			id: 3,
			name: "Bear Island",
			location: "North"
		)
	}
}