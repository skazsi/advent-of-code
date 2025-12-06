package skazsi.adventofcode2025.dec6;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

public class MathExercisesTest {

	@Test
	void part1_example() {
		// Given
		MathExercises underTest = new MathExercises(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(4277556l);
	}

	@Test
	void part1_input() {
		// Given
		MathExercises underTest = new MathExercises(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(3261038365331l);
	}

	@Test
	void part2_example() {
		// Given
		MathExercises underTest = new MathExercises(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(3263827l);
	}

	@Test
	void part2_input() {
		// Given
		MathExercises underTest = new MathExercises(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(8342588849093l);
	}

	private static class MathExercises {

		private List<List<String>> digits;
		private String[] operators;

		private MathExercises(InputStream inputStream) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				List<String> allLines = reader.lines().toList();

				operators = allLines.get(allLines.size() - 1).split("\\s(?=[\\+\\*])");
				
				digits = new ArrayList<>(allLines.size() - 2);
				allLines.stream().limit(allLines.size() - 1).forEach(line -> {
					digits.add(new ArrayList<>());
				});
				
				int startPositionOfColumn = 0;
				for (int column = 0; column < operators.length; column++) {
					for (int row = 0; row < allLines.size() - 1; row++) {
						digits.get(row).add(allLines.get(row).substring(startPositionOfColumn, startPositionOfColumn + operators[column].length()));
					}
					startPositionOfColumn += operators[column].length() + 1;
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private interface NumberSupplier {
			LongStream get(int column);
		}

		private class NormalNumberSupplier implements NumberSupplier {

			@Override
			public LongStream get(int column) {
				long[] numbersInColumn = new long[digits.size()];
				for (int row = 0; row < digits.size(); row++) {
					numbersInColumn[row] = Long.parseLong(digits.get(row).get(column).trim());
				}
				return LongStream.of(numbersInColumn);
			}
		}

		private class CephalopodNumberSupplier implements NumberSupplier {

			@Override
			public LongStream get(int column) {
				long[] numbersInColumn = new long[operators[column].length()];
				for (int i = numbersInColumn.length; i > 0; i--) {

					StringBuilder sb = new StringBuilder();
					for (int row = 0; row < digits.size(); row++) {
						sb.append(digits.get(row).get(column).charAt(i-1));
					}
					numbersInColumn[i-1] = Long.parseLong(sb.toString().trim());
				}
				return LongStream.of(numbersInColumn);
			}
		}

		private long part1() {
			return calculate(new NormalNumberSupplier());
		}

		private long part2() {
			return calculate(new CephalopodNumberSupplier());
		}

		private long calculate(NumberSupplier numberSupplier) {
			long sum = 0;
			for (int column = 0; column < operators.length; column++) {

				switch (operators[column].trim()) {
				case "+":
					sum += numberSupplier.get(column).reduce(0, (a, b) -> a + b);
					break;
				case "*":
					sum += numberSupplier.get(column).reduce(1, (a, b) -> a * b);
					break;
				}
			}
			return sum;
		}
	}		
}
