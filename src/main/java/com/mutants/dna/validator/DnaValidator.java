package com.mutants.dna.validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mutants.dna.dto.DnaDto;

@Service
public class DnaValidator implements Validator {

	private static final Pattern pattern = Pattern.compile("[^ACTG]");

	@Override
	public boolean supports(Class<?> clazz) {
		return DnaDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if (Objects.isNull(target)) {
			errors.reject("dataError", "It's imposible to check the dna. Null object.");
			return;
		}

		DnaDto dnaDto = (DnaDto) target;
		String[] dnaChains = dnaDto.getDna();

		if (Objects.isNull(dnaChains)) {
			errors.reject("dataError", "It's imposible to check the dna. The array string is null.");
			return;
		}

		int matrixOrderY = dnaChains.length;

		if (matrixOrderY <= 3) {
			errors.rejectValue("dna", "matrixError", "The matrix order is smaller than the minimun size");
			return;
		}

		Matcher matcher = pattern.matcher("");
		// Is it a square matrix?
		// Are all its letters A, T, C or G?
		for (String dna : dnaChains) {
			if (dna.length() != matrixOrderY) {
				errors.rejectValue("dna", "matrixError", "The matrix isn´t square");
				return;
			}

			matcher.reset(dna);
			if (matcher.find()) {
				errors.rejectValue("dna", "matrixError", "The matrix has invalid data. Not valid DNA.");
				return;
			}
		}

	}

}
