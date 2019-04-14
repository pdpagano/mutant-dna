package com.mutants.dna.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mutants.dna.domain.Dna;
import com.mutants.dna.enumeration.DnaType;

public interface DnaRepository extends MongoRepository<Dna, String>{
	public Dna findByDna(String[] dna);

	public int countByDnaType(DnaType dna);
}
