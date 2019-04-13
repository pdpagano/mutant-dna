package com.mutants.dna.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getApiURL() {
		return ResponseEntity.ok().build();
	}

}
