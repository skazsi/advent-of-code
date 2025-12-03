package skazsi.adventofcode2025.dec2;

import static java.lang.Math.abs;
import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class InvalidIdTest {

	@Test
	void part1_example() {
		// Given
		InvalidIdSearcherPart1 underTest = new InvalidIdSearcherPart1(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.result();

		// Then
		then(result).isEqualTo(1227775554);
	}

	@Test
	void part1_input() {
		// Given
		InvalidIdSearcherPart1 underTest = new InvalidIdSearcherPart1(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.result();

		// Then
		then(result).isEqualTo(17077011375l);
	}

	@Test
	void part2_example() {
		// Given
		InvalidIdSearcherPart2 underTest = new InvalidIdSearcherPart2(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.result();

		// Then
		then(result).isEqualTo(4174379265l);
	}

	@Test
	void part2_input() {
		// Given
		InvalidIdSearcherPart2 underTest = new InvalidIdSearcherPart2(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.result();

		// Then
		then(result).isEqualTo(36037497037l);
	}

	private static class InvalidIdSearcherPart1 {
		long result = 0;

		private InvalidIdSearcherPart1(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter(",");
				scanner.tokens().forEach(this::parseRange);
			}
		}

		private void parseRange(String line) {
			long start = Long.parseLong(line.substring(0, line.indexOf("-")));
			long end = Long.parseLong(line.substring(line.indexOf("-") + 1));
			System.out.println("Range: " + line + " (" + (end - start) + ")");

			result += LongStream.rangeClosed(start, end)
					.filter(number -> Long.toString(number).length() % 2 == 0)
					.peek(number -> System.out.println("\t\tChecking: " + number))
					.filter(number -> {
						String asString = Long.toString(number);
						String first = asString.substring(0, asString.length() / 2);
						return Long.parseLong(first + first) == number;
					})
					.peek(number -> System.out.println("\tCounting: " + number))
					.sum();
		}

		private long result() {
			return result;
		}
	}

	private static class InvalidIdSearcherPart2 {
		long result = 0;

		private InvalidIdSearcherPart2(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter(",");
				scanner.tokens().forEach(this::parseRange);
			}
		}

		private void parseRange(String line) {
			long start = Long.parseLong(line.substring(0, line.indexOf("-")));
			long end = Long.parseLong(line.substring(line.indexOf("-") + 1));
			System.out.println("Range: " + line + " (" + (end - start) + ")");

			result += LongStream.rangeClosed(start, end)
					.filter(this::containsRepeatedSequenceOfDigits)
					.peek(number -> System.out.println("\t\tCounting: " + number))
					.sum();
		}

		private boolean containsRepeatedSequenceOfDigits(long number) {
			String asString = Long.toString(number);
			int length = asString.length();
			for (int seqLength = 1; seqLength <= length / 2; seqLength++) {
				String sequence = asString.substring(0, seqLength);
				StringBuilder repeated = new StringBuilder();
				while (repeated.length() < length) {
					repeated.append(sequence);
				}
				if (repeated.toString().equals(asString)) {
					return true;
				}
			}
			return false;
			
		}
		
		private long result() {
			return result;
		}
	}
}
