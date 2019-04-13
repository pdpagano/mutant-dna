package com.mutants.dna.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mutants.dna.enumeration.DnaType;

@Document(collection = "dnachains")
public class Dna {

	@Id
	private String id;
	private DnaType dnaType;
	private String[] dna;

	public Dna() {
	}

	public Dna(String[] dna) {
		this.dnaType = DnaType.UNDEFINED;
		this.dna = dna;
	}

	public Dna(DnaType dnaType, String[] dna) {
		this.dnaType = dnaType;
		this.dna = dna;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	public DnaType getDnaType() {
		return dnaType;
	}

	public void setDnaType(DnaType dnaType) {
		this.dnaType = dnaType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
