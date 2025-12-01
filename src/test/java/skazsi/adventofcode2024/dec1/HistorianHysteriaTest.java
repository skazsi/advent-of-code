package skazsi.adventofcode2024.dec1;

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

public class HistorianHysteriaTest {

	@Test
	void part1_example() {
		// Given
		LineMapper underTest = new LineMapper(getClass().getResourceAsStream("part1_sample"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(11);
	}

	@Test
	void part1_input() {
		// Given
		LineMapper underTest = new LineMapper(getClass().getResourceAsStream("part1_input"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(2970687);
	}

	@Test
	void part2_example() {
		// Given
		LineMapper underTest = new LineMapper(getClass().getResourceAsStream("part1_sample"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(31);
	}

	@Test
	void part2_input() {
		// Given
		LineMapper underTest = new LineMapper(getClass().getResourceAsStream("part1_input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(23963899);
	}

	private static class LineMapper {

		private List<Integer> firstList = new ArrayList<>();
		private List<Integer> secondList = new ArrayList<>();

		LineMapper(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n");
				scanner.tokens().forEach(this::parseLine);
			}
			Collections.sort(firstList);
			Collections.sort(secondList);
		}

		private void parseLine(String text) {
			try (Scanner scanner = new Scanner(text)) {
				scanner.useDelimiter("   ");
				firstList.add(Integer.parseInt(scanner.next()));
				secondList.add(Integer.parseInt(scanner.next()));
			}
		}

		long part1() {
			long sumOfDistances = 0;
			for (int i = 0; i < firstList.size(); i++) {
				int first = firstList.get(i);
				int second = secondList.get(i);
				sumOfDistances += abs(second - first);
			}
			return sumOfDistances;
		}

		long part2() {
			long sumOfSimilarities = 0;
			for (int i = 0; i < firstList.size(); i++) {
				int first = firstList.get(i);
				long count = secondList.stream().filter(number -> number == first).count();
				sumOfSimilarities += count * first;
			}
			return sumOfSimilarities;
		}
	}
}
