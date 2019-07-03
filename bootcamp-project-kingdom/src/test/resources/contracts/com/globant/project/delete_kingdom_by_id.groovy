package com.globant.project

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should delete kingdom with id=3"

	request {
		url "/kingdoms/3"
		method DELETE()
	}

	response {
		status OK()
	}
}