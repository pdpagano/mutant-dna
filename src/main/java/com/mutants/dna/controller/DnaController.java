package com.mutants.dna.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pdpagano@gmail.com
 *
 */
@RestController
@RequestMapping("dnachains")
@Api( tags = "DNAs")
public class DnaController {

	@Autowired
	private DnaValidator dnaValidator;

	@Autowired
	private DnaService dnaService;

	@ApiOperation(value = "Determines if a given DNA belongs to a human or a mutant.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "You have detected a mutant!"),
	        @ApiResponse(code = 400, message = "Not a valid DNA", response = ObjectError.class),
	        @ApiResponse(code = 403, message = "The DNA belongs to a human")
	})
	@RequestMapping(value = "/mutant", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> checkDNA(@RequestBody DnaDto dnaDto) {
		Errors errors = new BeanPropertyBindingResult(dnaDto, "dnaDto");
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

	@ApiOperation(value = "Returns statistics about the different DNA that have been analysed")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "statistics calculated and returned")
	})
	@RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<StatsDto> getStats() {
		StatsDto statsDto = dnaService.calculateStats();
		return ResponseEntity.ok(statsDto);
	}

}
