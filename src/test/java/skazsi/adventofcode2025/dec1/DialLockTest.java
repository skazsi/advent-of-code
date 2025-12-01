package skazsi.adventofcode2025.dec1;

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

import org.junit.jupiter.api.Test;

public class DialLockTest {

	@Test
	void part1_example() {
		// Given
		DialLock underTest = new DialLock(getClass().getResourceAsStream("sample"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(3);
	}

	@Test
	void part1_input() {
		// Given
		DialLock underTest = new DialLock(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(1018);
	}

	@Test
	void part2_example() {
		// Given
		DialLock underTest = new DialLock(getClass().getResourceAsStream("sample"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(6);
	}

	@Test
	void part2_input() {
		// Given
		DialLock underTest = new DialLock(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(5815);
	}

	private static class DialLock {

		private int position = 50;
		private int pointOverZero = 0;
		private int pointAtZero = 0;

		private DialLock(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n");
				scanner.tokens().forEach(this::parseLine);
			}
		}

		private int part1() {
			return pointAtZero;
		}

		private int part2() {
			return pointAtZero + pointOverZero;
		}

		private void parseLine(String line) {
			int clicks = Integer.parseInt(line.substring(1));

			pointOverZero += clicks / 100;
			clicks = clicks % 100;

			if (clicks != 0) {
				switch (line.charAt(0)) {
				case 'R':
					rotateRight(clicks);
					break;
				case 'L':
					rotateLeft(clicks);
					break;
				}
			}
			System.out.println(line + " -> " + position + " (over0: " + pointOverZero + ", at0: " + pointAtZero + ")");
		}

		private void rotateRight(int clicks) {
			if (position + clicks > 100) {
				pointOverZero++;
			}
			position = (position + clicks) % 100;
			if (position == 0) {
				pointAtZero++;
			}
		}

		private void rotateLeft(int clicks) {
			if (position > 0 && position - clicks < 0) {
				pointOverZero++;
			}
			position = (position + 100 - clicks) % 100;
			if (position == 0) {
				pointAtZero++;
			}
		}
	}
}
