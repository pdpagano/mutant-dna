package com.mutants.dna.service;

import com.mutants.dna.dto.StatsDto;
import com.mutants.dna.domain.Dna;

public interface DnaService {
	public boolean isMutant(String[] dnaChains);
	public Dna findDna(Dna dna);
	public Dna saveDna(Dna dna);
	public StatsDto calculateStats();
}
