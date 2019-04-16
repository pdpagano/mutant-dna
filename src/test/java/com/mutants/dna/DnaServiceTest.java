package com.mutants.dna;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mutants.dna.service.impl.DnaServiceImpl;
import com.mutants.dna.domain.Dna;
import com.mutants.dna.dto.StatsDto;
import com.mutants.dna.enumeration.DnaType;
import com.mutants.dna.repository.DnaRepository;
import com.mutants.dna.service.DnaService;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DnaServiceTest {

	@TestConfiguration
	static class contextConfiguration {
		@Bean
		public DnaService DnaService() {
			return new DnaServiceImpl();
		}
	}

	@Autowired
	private DnaService dnaService;

	@MockBean
	private DnaRepository dnaRepository;

	private Dna humanDna;

	@Before
	public void setUp() {
		String[] humanDnaChains = { "ACTG", "AACC", "TGAC", "CCTT" };

		humanDna = new Dna();
		humanDna.setDna(humanDnaChains);
		humanDna.setDnaType(DnaType.HUMAN);
		humanDna.setId("dna1");
	}

	@Test
	public void ShouldReturnNullWhenTryToFindNullDna() {
		Dna dna = dnaService.findDna(null);
		Assert.assertNull(dna);
	}

	@Test
	public void ShouldReturnDnaWhenDnaExists() {

		when(dnaRepository.findByDna(any(String[].class))).thenReturn(humanDna);

		Dna dna = dnaService.findDna(humanDna);

		Assert.assertNotNull(dna);
		Assert.assertArrayEquals(humanDna.getDna(), dna.getDna());
		Assert.assertEquals(humanDna.getDnaType(), dna.getDnaType());
		Assert.assertEquals(humanDna.getId(), dna.getId());

	}

	@Test
	public void ShouldReturnNullWhenTryToSaveNullDna() {
		Dna dna = dnaService.saveDna(null);
		Assert.assertNull(dna);
	}

	@Test
	public void ShouldReturnDnaWhenSaveDna() {

		when(dnaRepository.save(any(Dna.class))).thenReturn(humanDna);

		Dna dna = dnaService.saveDna(humanDna);

		Assert.assertNotNull(dna);
		Assert.assertArrayEquals(humanDna.getDna(), dna.getDna());
		Assert.assertEquals(humanDna.getDnaType(), dna.getDnaType());
		Assert.assertEquals(humanDna.getId(), dna.getId());

	}

	@Test
	public void ShouldGetStatsRatioCeroWhenCeroHumans() {

		when(dnaRepository.countByDnaType(DnaType.HUMAN)).thenReturn(0);
		when(dnaRepository.countByDnaType(DnaType.MUTANT)).thenReturn(20);

		StatsDto stats = dnaService.calculateStats();
		
		Assert.assertNotNull(stats);
		Assert.assertEquals(0,stats.getCountHumanDna());
		Assert.assertEquals(20,stats.getCountMutantDna());
		Assert.assertEquals(0f,stats.getRatio(),0);
	}
	
	@Test
	public void ShouldGetStatsWhenRequested() {

		when(dnaRepository.countByDnaType(DnaType.HUMAN)).thenReturn(40);
		when(dnaRepository.countByDnaType(DnaType.MUTANT)).thenReturn(20);

		StatsDto stats = dnaService.calculateStats();
		
		Assert.assertNotNull(stats);
		Assert.assertEquals(40,stats.getCountHumanDna());
		Assert.assertEquals(20,stats.getCountMutantDna());
		Assert.assertEquals(0.5f,stats.getRatio(),0.0001);
	}
	
	@Test
	public void ShouldReturnFalseWhenDnaIsHuman() {
		String[] humanDnaChains = humanDna.getDna();
		boolean result = dnaService.isMutant(humanDnaChains);
		Assert.assertFalse(result);
	}
	
	@Test
	public void ShouldReturnTrueWhenDnaIsMutantByRows() {
		String[] dnaChains = {"AAAAA","CCCCC","CTGAC","GGTTG","AACGT"};
		boolean result = dnaService.isMutant(dnaChains);
		Assert.assertTrue(result);
	}
	
	@Test
	public void ShouldReturnTrueWhenDnaIsMutantByColumns() {
		String[] dnaChains = {"ACGG","ACTT","ACCC","ACGG"};
		boolean result = dnaService.isMutant(dnaChains);
		Assert.assertTrue(result);
	}
	
	@Test
	public void ShouldReturnTrueWhenDnaIsMutantByDescDiag() {
		String[] dnaChains = {"ACGGA","CACTT","TGACG","ACAAC","GTTAT"};
		boolean result = dnaService.isMutant(dnaChains);
		Assert.assertTrue(result);
	}
	
	@Test
	public void ShouldReturnTrueWhenDnaIsMutantByDescDiagLow() {
		String[] dnaChains = {"AGGGA","CACTT","TCAGG","ACCAA","GTTCA"};
		boolean result = dnaService.isMutant(dnaChains);
		Assert.assertTrue(result);
	}
	
	@Test
	public void ShouldReturnTrueWhenDnaIsMutantByAscDiag() {
		String[] dnaChains = {"TTCCG","ATGAC","TAACG","GACGG","ACAAA"};
		boolean result = dnaService.isMutant(dnaChains);
		Assert.assertTrue(result);
	}
	
	@Test
	public void ShouldReturnTrueWhenDnaIsMutantByAscDiagHight() {
		String[] dnaChains = {"GGGTA","GGTAC","GTACG","TAAGA","ATGTC"};
		boolean result = dnaService.isMutant(dnaChains);
		Assert.assertTrue(result);
	}

}
