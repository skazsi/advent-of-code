package skazsi.adventofcode2025.dec7;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class TachyonBeamTest {

	@Test
	void part1_example() {
		// Given
		TachyonBeam underTest = new TachyonBeam(getClass().getResourceAsStream("sample"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(21);
	}

	@Test
	void part1_input() {
		// Given
		TachyonBeam underTest = new TachyonBeam(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(1628);
	}

	@Test
	void part2_example() {
		// Given
		TachyonBeam underTest = new TachyonBeam(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(40);
	}

	@Test
	void part2_input() {
		// Given
		TachyonBeam underTest = new TachyonBeam(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(27055852018812l);
	}

	private static class TachyonBeam {

		private long[] beams;
		private int splittingCount;

		private TachyonBeam(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n");
				scanner.tokens().forEach(this::processLine);
			}
		}

		private void processLine(String line) {
			if (beams == null) {
				initializeBeam(line);
			}

			StringBuilder debug = new StringBuilder();
			debug.append(line);

			long[] newBeams = new long[beams.length];
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '^' && beams[i] > 0) {
					newBeams[i - 1] = newBeams[i - 1] + beams[i];
					newBeams[i + 1] = newBeams[i + 1] + beams[i];

					debug.setCharAt(i - 1, '|');
					debug.setCharAt(i, '^');

					splittingCount++;
				} else if (line.charAt(i) == '^') {
					debug.setCharAt(i, 'X');
				} else {
					newBeams[i] = newBeams[i] + beams[i];
					debug.setCharAt(i, newBeams[i] > 0 ? '|' : ' ');
				}
			}
			beams = newBeams;
			
			System.out.println(debug.toString() + "   splittingCount: " + splittingCount);

//			for (int i = 0; i < beams.length; i++) {
//				System.out.print(beams[i] > 0 ? "|" : ".");
//			}
//			System.out.println();

//			for (int i = 0; i < beams.length; i++) {
//				System.out.print(beams[i] > 0 ? String.format("%06d", beams[i]) : "      ");
//			}
//			int timelineCount = 0;
//			for (int i = 0; i < beams.length; i++) {
//				timelineCount += beams[i];
//			}
//			System.out.println(" splittingCount: " + splittingCount + ", timelines: " + timelineCount);
		}

		private void initializeBeam(String line) {
			beams = new long[line.length()];
			beams[line.indexOf("S")] = 1;
		}

		private int part1() {
			return splittingCount;
		}

		private long part2() {
			long timelineCount = 0;
			for (int i = 0; i < beams.length; i++) {
				timelineCount += beams[i];
			}
			return timelineCount;
		}
	}
}
