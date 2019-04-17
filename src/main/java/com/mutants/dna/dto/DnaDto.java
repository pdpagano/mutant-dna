package com.mutants.dna.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents a matrix with DNA sequences")
public class DnaDto {

	@ApiModelProperty(value = "DNA chain compuse by only four letters A,T,C,G.", example = "[\"ATCG\",\"AAAA\",\"ACGG\",\"AGTT\"]")
	private String[] dna;

	public DnaDto() {}
	
	public DnaDto(String[] dna) {
		super();
		this.dna = dna;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
}
