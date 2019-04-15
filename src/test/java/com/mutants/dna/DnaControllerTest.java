package com.mutants.dna;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mutants.dna.controller.DnaController;
import com.mutants.dna.domain.Dna;
import com.mutants.dna.dto.StatsDto;
import com.mutants.dna.enumeration.DnaType;
import com.mutants.dna.service.DnaService;
import com.mutants.dna.validator.DnaValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(DnaController.class)
public class DnaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DnaService dnaService;
	
	@MockBean
	private DnaValidator dnaValidator;

	@Test
	public void ShouldReturnForbiddenWhenDnaIsNewAndHuman() throws Exception {

		when(dnaService.findDna(any())).thenReturn(null);
		when(dnaService.isMutant(any())).thenReturn(false);

		mvc.perform(post("/dnachains/mutant").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void ShouldReturnOkWhenDnaIsNewAndMutant() throws Exception {

		when(dnaService.findDna(any())).thenReturn(null);
		when(dnaService.isMutant(any())).thenReturn(true);

		mvc.perform(post("/dnachains/mutant").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void ShouldReturnForbiddenWhenDnaExistsAndIsHuman() throws Exception {
		
		String[] humanDnaChains = {"ACTG","AACC","TGAC","CCTT"};
		
		Dna humanDna = new Dna();
		humanDna.setDna(humanDnaChains);
		humanDna.setDnaType(DnaType.HUMAN);
		
		when(dnaService.findDna(any())).thenReturn(humanDna);
		
		mvc.perform(post("/dnachains/mutant").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void ShouldReturnOkWhenDnaExistsAndIsMutant() throws Exception {
		
		String[] mutantDnaChains = {"AAAA","AAAA","CCCC","CCCC"};
		
		Dna humanDna = new Dna();
		humanDna.setDna(mutantDnaChains);
		humanDna.setDnaType(DnaType.MUTANT);
		
		when(dnaService.findDna(any())).thenReturn(humanDna);
		
		mvc.perform(post("/dnachains/mutant").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void ShouldReturnStats() throws Exception {

		StatsDto statsDto = new StatsDto(20, 40, 0.5f);

		when(dnaService.calculateStats()).thenReturn(statsDto);

		mvc.perform(get("/dnachains/stats"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'count_mutant_dna':20,'count_human_dna':40,'ratio':0.5}"));

	}
	
}
