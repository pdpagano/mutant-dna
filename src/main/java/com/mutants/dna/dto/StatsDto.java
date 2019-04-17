package com.mutants.dna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Analysis statistics")
public class StatsDto {

	@ApiModelProperty(value = "Number of mutants detected", example = "40")
	private int countMutantDna;
	@ApiModelProperty(value = "Number of humans detected", example = "100")
	private int countHumanDna;
	@ApiModelProperty(value = "Ratio between mutant count and human count", example = "0.4")
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
