package skazsi.adventofcode2025.dec4;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class RollsOfPaperTest {

	@Test
	void part1_example() {
		// Given
		RoolsOfPaper underTest = new RoolsOfPaper(getClass().getResourceAsStream("sample"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(13);
	}

	@Test
	void part1_input() {
		// Given
		RoolsOfPaper underTest = new RoolsOfPaper(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part1();

		// Then
		then(result).isEqualTo(1495);
	}

	@Test
	void part2_example() {
		// Given
		RoolsOfPaper underTest = new RoolsOfPaper(getClass().getResourceAsStream("sample"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(43);
	}

	@Test
	void part2_input() {
		// Given
		RoolsOfPaper underTest = new RoolsOfPaper(getClass().getResourceAsStream("input"));

		// When
		int result = underTest.part2();

		// Then
		then(result).isEqualTo(8768);
	}

	private static class RoolsOfPaper {

		private final int verticalSize;
		private final int horizontalSize;
		private final boolean[][] charMatrix;

		private RoolsOfPaper(InputStream inputStream) {
			try (Scanner scanner = new Scanner(inputStream)) {
				scanner.useDelimiter("\r\n");
				charMatrix = scanner.tokens().map(this::mapLineToBooleans).toArray(boolean[][]::new);
			}

			verticalSize = charMatrix.length;
			horizontalSize = charMatrix[0].length;
		}

		private boolean[] mapLineToBooleans(String line) {
			boolean[] booleans = new boolean[line.length()];
			for (int i = 0; i < line.length(); i++) {
				booleans[i] = line.charAt(i) == '@';
			}
			return booleans;
		}

		private int part1() {
			int count = 0;
			boolean[][] removableRools = findRemovableRools();
			for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
				for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
					if (removableRools[verticalPosition][horizontalPosition]) {
						count++;
						charMatrix[verticalPosition][horizontalPosition] = false;
					}
				}
			}
			return count;
		}

		private int part2() {
			int count = 0;
			int previousCount;
			while ((previousCount = part1()) > 0) {
				count += previousCount;
			}
			return count;
		}
		
		private boolean[][] findRemovableRools() {
			boolean[][] result = new boolean[verticalSize][horizontalSize];
			for (int verticalPosition = 0; verticalPosition < verticalSize; verticalPosition++) {
				for (int horizontalPosition = 0; horizontalPosition < horizontalSize; horizontalPosition++) {
					if (charMatrix[verticalPosition][horizontalPosition]
							&& numberOfAdjacentRolls(verticalPosition, horizontalPosition) < 4) {
						result[verticalPosition][horizontalPosition] = true;
					}
				}
			}
			return result;
		}

		private int numberOfAdjacentRolls(int verticalPosition, int horizontalPosition) {
			int count = 0;
			count += isTrueSafely(verticalPosition - 1, horizontalPosition - 1) ? 1 : 0;
			count += isTrueSafely(verticalPosition - 1, horizontalPosition) ? 1 : 0;
			count += isTrueSafely(verticalPosition - 1, horizontalPosition + 1) ? 1 : 0;

			count += isTrueSafely(verticalPosition, horizontalPosition - 1) ? 1 : 0;
			count += isTrueSafely(verticalPosition, horizontalPosition + 1) ? 1 : 0;

			count += isTrueSafely(verticalPosition + 1, horizontalPosition - 1) ? 1 : 0;
			count += isTrueSafely(verticalPosition + 1, horizontalPosition) ? 1 : 0;
			count += isTrueSafely(verticalPosition + 1, horizontalPosition + 1) ? 1 : 0;
			return count;
		}

		private boolean isTrueSafely(int verticalPosition, int horizontalPosition) {
			if (verticalPosition < 0 || verticalPosition >= verticalSize || horizontalPosition < 0
					|| horizontalPosition >= horizontalSize) {
				return false;
			}
			return charMatrix[verticalPosition][horizontalPosition];
		}
	}
}
