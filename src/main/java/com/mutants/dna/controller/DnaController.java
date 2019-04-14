package com.mutants.dna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mutants.dna.dto.DnaDto;
import com.mutants.dna.service.DnaService;
import com.mutants.dna.validator.DnaValidator;

@RestController
@RequestMapping("dnachains")
public class DnaController {
	
	@Autowired
	private DnaValidator dnaValidator;
	
	@Autowired
	private DnaService dnaService;

	@RequestMapping(value = "/mutant",method = RequestMethod.POST)
	public ResponseEntity<?> checkDNA(@RequestBody DnaDto dnaDto, Errors errors) {
		dnaValidator.validate(dnaDto, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors.getAllErrors());
		}
		if(dnaService.isMutant(dnaDto.getDna())) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
}
