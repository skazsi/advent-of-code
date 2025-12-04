package skazsi.adventofcode2025.dec3;

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

public class BatteriesTest {

	@Test
	void part1_example() {
		// Given
		Batteries underTest = new Batteries(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(357l);
	}

	@Test
	void part1_input() {
		// Given
		Batteries underTest = new Batteries(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(17443l);
	}

	@Test
	void part2_example() {
		// Given
		Batteries underTest = new Batteries(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(3121910778619l);
	}

	@Test
	void part2_input() {
		// Given
		Batteries underTest = new Batteries(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(172167155440541l);
	}

	private static class Batteries {
		private InputStream inputStream;
		private long result;

		private Batteries(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		private long part1() {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n");
				scanner.tokens().map(this::parseDigits).map(digits -> highestNDigits(digits, 2)).forEach(value -> result += value);
			}
			return result;
		}

		private long part2() {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n");
				scanner.tokens().map(this::parseDigits).map(digits -> highestNDigits(digits, 12)).forEach(value -> result += value);
			}
			return result;
		}
		
		private int[] parseDigits(String line) {
			return line.chars().map(c -> c - '0').toArray();
		}

		private long highestNDigits(int[] digits, int n) {
			StringBuilder sb = new StringBuilder();
			int position = -1;
			for (int i = 0; i < n; i++) {
				position = positionOfMaxDigit(digits, position + 1, digits.length - (n - i - 1));
				sb.append(digits[position]);
			}
			return Long.parseLong(sb.toString());
		}
		
		private int positionOfMaxDigit(int[] digits, int start, int end) {
			int maxPos = start;
			for (int i = start + 1; i < end; i++) {
				if (digits[i] > digits[maxPos]) {
					maxPos = i;
				}
			}
			return maxPos;
		}
	}
}
