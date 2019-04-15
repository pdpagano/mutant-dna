package com.mutants.dna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsDto {

	private int countMutantDna;
	private int countHumanDna;
	private float ratio;

	public StatsDto() {
	}

	public StatsDto(int countMutantDna, int countHumanDna, float ratio) {
		super();
		this.countMutantDna = countMutantDna;
		this.countHumanDna = countHumanDna;
		this.ratio = ratio;
	}

	@JsonProperty("count_mutant_dna")
	public int getCountMutantDna() {
		return countMutantDna;
	}

	public void setCountMutantDna(int countMutantDna) {
		this.countMutantDna = countMutantDna;
	}

	@JsonProperty("count_human_dna")
	public int getCountHumanDna() {
		return countHumanDna;
	}

	public void setCountHumanDna(int countHumanDna) {
		this.countHumanDna = countHumanDna;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

}
