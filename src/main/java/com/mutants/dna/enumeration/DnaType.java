package com.mutants.dna.enumeration;

public enum DnaType {

	HUMAN("Human"), MUTANT("Mutant"), UNDEFINED("Undefined");
	
	private String dnaTypeName;
	
	private DnaType(String dnaTypeName) {
		this.dnaTypeName = dnaTypeName;
	}

	public String getDnaTypeName() {
		return this.dnaTypeName;
	}
	
}
