package com.mutants.dna.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mutants.dna.domain.Dna;
import com.mutants.dna.dto.DnaDto;
import com.mutants.dna.dto.StatsDto;
import com.mutants.dna.enumeration.DnaType;
import com.mutants.dna.service.DnaService;
import com.mutants.dna.validator.DnaValidator;

/**
 * @author pdpagano@gmail.com
 *
 */
@RestController
@RequestMapping("dnachains")
public class DnaController {

	@Autowired
	private DnaValidator dnaValidator;

	@Autowired
	private DnaService dnaService;

	@RequestMapping(value = "/mutant", method = RequestMethod.POST)
	public ResponseEntity<?> checkDNA(@RequestBody DnaDto dnaDto, Errors errors) {

		dnaValidator.validate(dnaDto, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors.getAllErrors());
		}

		Dna dna = dnaService.findDna(new Dna(dnaDto.getDna()));
		if (Objects.nonNull(dna) && dna.getDnaType().equals(DnaType.HUMAN)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (Objects.nonNull(dna) && dna.getDnaType().equals(DnaType.MUTANT)) {
			return ResponseEntity.ok().build();
		}

		if (dnaService.isMutant(dnaDto.getDna())) {
			dnaService.saveDna(new Dna(DnaType.MUTANT, dnaDto.getDna()));
			return ResponseEntity.ok().build();
		}

		dnaService.saveDna(new Dna(DnaType.HUMAN, dnaDto.getDna()));

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

	}

	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public ResponseEntity<?> getStats() {
		StatsDto statsDto = dnaService.calculateStats();
		return ResponseEntity.ok(statsDto);
	}

}
