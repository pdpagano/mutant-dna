package com.mutants.dna;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mutants.dna.controller.DnaController;
import com.mutants.dna.service.DnaService;
import com.mutants.dna.validator.DnaValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(DnaController.class)
public class DnaControllerValidatorIT {

	@TestConfiguration
	static class contextConfiguration {
		@Bean
		public DnaValidator DnaValidator() {
			return new DnaValidator();
		}
	}
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private DnaService dnaService;
	
	@Test
	public void ShouldReturnBadRequesWhenValidationFails() throws Exception {
		
		mvc.perform(post("/dnachains/mutant").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("[{'code':'dataError'}]"))
				.andDo(print());
	}
	
}
