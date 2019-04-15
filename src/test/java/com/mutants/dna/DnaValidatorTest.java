package com.mutants.dna;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.mutants.dna.dto.DnaDto;
import com.mutants.dna.validator.DnaValidator;

@RunWith(SpringRunner.class)
public class DnaValidatorTest {

	@TestConfiguration
	static class contextConfiguration {
		@Bean
		public DnaValidator DnaValidator() {
			return new DnaValidator();
		}
	}

	@Autowired
	private DnaValidator dnaValidator;

	@Test
	public void ShouldRejectTargetWhenItIsNull() {

		Errors errors = new BeanPropertyBindingResult(null, "dnaDto");

		dnaValidator.validate(null, errors);

		Assert.assertTrue(errors.hasGlobalErrors());

	}

	@Test
	public void ShouldRejectDnaDtotWhenDnaArrayItIsNull() {

		DnaDto dnaDto = new DnaDto(null);
		Errors errors = new BeanPropertyBindingResult(dnaDto, "dnaDto");

		dnaValidator.validate(dnaDto, errors);

		Assert.assertTrue(errors.hasGlobalErrors());

	}

	@Test
	public void ShouldRejectDnaDtotWhenDnaArrayIsSmallerThanMin() {

		DnaDto dnaDto = new DnaDto(new String[] {});
		Errors errors = new BeanPropertyBindingResult(dnaDto, "dnaDto");

		dnaValidator.validate(dnaDto, errors);

		Assert.assertTrue(errors.hasErrors());
		Assert.assertTrue(errors.hasFieldErrors("dna"));

	}
	
	@Test
	public void ShouldRejectDnaDtotWhenDnaArrayIsNotSquare() {

		DnaDto dnaDto = new DnaDto(new String[] {"AAAA","AAAA","CGTA","A"});
		Errors errors = new BeanPropertyBindingResult(dnaDto, "dnaDto");

		dnaValidator.validate(dnaDto, errors);

		Assert.assertTrue(errors.hasErrors());
		Assert.assertTrue(errors.hasFieldErrors("dna"));

	}
	
	@Test
	public void ShouldRejectDnaDtotWhenDnaArrayHasInvalidLetters() {

		DnaDto dnaDto = new DnaDto(new String[] {"XAAA","AAAA","CGTA","AAAA"});
		Errors errors = new BeanPropertyBindingResult(dnaDto, "dnaDto");

		dnaValidator.validate(dnaDto, errors);

		Assert.assertTrue(errors.hasErrors());
		Assert.assertTrue(errors.hasFieldErrors("dna"));

	}

}
