package com.mutants.dna.service.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mutants.dna.domain.Dna;
import com.mutants.dna.dto.DtoStats;
import com.mutants.dna.enumeration.DnaType;
import com.mutants.dna.repository.DnaRepository;
import com.mutants.dna.service.DnaService;

@Service
public class DnaServiceImpl implements DnaService {

	private static final Pattern pattern = Pattern.compile("(?:A{4})|(?:T{4})|(?:C{4})|(?:G{4})");
	private static final int BOUNDARY_COUNT = 2;

	@Autowired
	DnaRepository dnaRepository;

	@Override
	public boolean isMutant(String[] dnaChains) {

		int matches = 0;

		matches = checkRows(dnaChains, matches);
		if (matches == BOUNDARY_COUNT)
			return true;

		matches = checkColumns(dnaChains, matches);
		if (matches == BOUNDARY_COUNT)
			return true;

		matches = checkAscDiagonals(dnaChains, matches);
		if (matches == BOUNDARY_COUNT)
			return true;

		matches = checkDescDiagonals(dnaChains, matches);
		if (matches == BOUNDARY_COUNT)
			return true;

		return false;
	}

	private int countMatches(String dna, int matches) {

		Matcher matcher = pattern.matcher(dna);

		while (matcher.find()) {
			matches++;
			if (matches == BOUNDARY_COUNT)
				return matches;
		}

		return matches;
	}

	private int checkRows(String[] dnaChains, int matches) {
		for (String dna : dnaChains) {
			matches = countMatches(dna, matches);
			if (matches == BOUNDARY_COUNT)
				return matches;
		}
		return matches;
	}

	private int checkColumns(String[] dnaChains, int matches) {

		int matrixOrder = dnaChains[0].length();

		for (int i = 0; i < matrixOrder; i++) {
			char[] tempColumn = new char[matrixOrder];
			for (int j = 0; j < matrixOrder; j++) {
				tempColumn[j] = dnaChains[j].charAt(i);
			}
			matches = countMatches(new String(tempColumn), matches);
			if (matches == BOUNDARY_COUNT)
				return matches;
		}

		return matches;
	}

	private int checkAscDiagonals(String[] dnaChains, int matches) {

		String diagonalTemp;

		int row;
		int col;

		for (row = 3; row < dnaChains.length; row++) {
			diagonalTemp = "";
			// first column
			col = 0;
			for (int rowAux = row; rowAux >= 0; rowAux--, col++) {
				diagonalTemp += dnaChains[rowAux].charAt(col);
			}
			matches = countMatches(diagonalTemp, matches);
			if (matches == BOUNDARY_COUNT)
				return matches;
		}

		for (col = 1; col <= dnaChains.length - 4; col++) {
			diagonalTemp = "";
			// last row
			row = dnaChains.length - 1;
			for (int colAux = col; colAux <= dnaChains.length - 1; row--, colAux++) {
				diagonalTemp += dnaChains[row].charAt(colAux);
			}
			matches = countMatches(diagonalTemp, matches);
			if (matches == BOUNDARY_COUNT)
				return matches;
		}

		return matches;

	}

	private int checkDescDiagonals(String[] dnaChains, int matches) {

		String diagonalTemp;

		int row;
		int col;

		for (col = dnaChains.length - 4; col >= 0; col--) {
			diagonalTemp = "";
			row = 0;
			for (int colAux = col; colAux < dnaChains.length; colAux++, row++) {
				diagonalTemp += dnaChains[row].charAt(colAux);
			}
			matches = countMatches(diagonalTemp, matches);
			if (matches == BOUNDARY_COUNT)
				return matches;
		}

		for (row = 1; row < dnaChains.length - 3; row++) {
			diagonalTemp = "";
			col = 0;
			for (int rowAux = row; rowAux < dnaChains.length; col++, rowAux++) {
				diagonalTemp += dnaChains[rowAux].charAt(col);
			}
			matches = countMatches(diagonalTemp, matches);
			if (matches == BOUNDARY_COUNT)
				return matches;
		}

		return matches;

	}

	@Override
	public Dna findDna(Dna dna) {
		if (Objects.isNull(dna)) {
			return null;
		}
		return dnaRepository.findByDna(dna.getDna());
	}

	@Override
	public Dna saveDna(Dna dna) {
		if (Objects.isNull(dna)) {
			return null;
		}
		return dnaRepository.save(dna);
	}

	@Override
	public DtoStats calculateStats() {
		int mutantCount = dnaRepository.countByDnaType(DnaType.MUTANT);
		int humanCount = dnaRepository.countByDnaType(DnaType.HUMAN);
		float ratio = humanCount == 0 ? 0 : mutantCount / (float) humanCount;
		return new DtoStats(mutantCount, humanCount, ratio);
	}

}
