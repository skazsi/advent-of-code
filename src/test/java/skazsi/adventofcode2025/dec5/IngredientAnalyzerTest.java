package skazsi.adventofcode2025.dec5;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class IngredientAnalyzerTest {

	@Test
	void part1_example() {
		// Given
		IngredientAnalyzer underTest = new IngredientAnalyzer(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(3);
	}

	@Test
	void part1_input() {
		// Given
		IngredientAnalyzer underTest = new IngredientAnalyzer(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part1();

		// Then
		then(result).isEqualTo(821);
	}

	@Test
	void part2_example() {
		// Given
		IngredientAnalyzer underTest = new IngredientAnalyzer(getClass().getResourceAsStream("sample"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(14l);
	}

	@Test
	void part2_input() {
		// Given
		IngredientAnalyzer underTest = new IngredientAnalyzer(getClass().getResourceAsStream("input"));

		// When
		long result = underTest.part2();

		// Then
		then(result).isEqualTo(344771884978261l);
	}

	private static class IngredientAnalyzer {

		private final List<IdRange> ranges = new ArrayList<IdRange>();
		private int validIdCount = 0;
		private long validRangeIdCount = 0;

		private IngredientAnalyzer(InputStream inputStream) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = reader.readLine()) != null && !line.isBlank()) {
					String[] parts = line.split("-");
					long from = Long.parseLong(parts[0]);
					long to = Long.parseLong(parts[1]);
					ranges.add(new IdRange(from, to));
				}

				while ((line = reader.readLine()) != null && !line.isBlank()) {
					long id = Long.parseLong(line);
					ranges.stream().filter(range -> range.contains(id)).findFirst().ifPresent(__ -> validIdCount++);
				}

				boolean inRange = false;
				long inRangeDeepth = 0;
				long lastPosition = Integer.MIN_VALUE;
				long position = Integer.MIN_VALUE;
				while (position < Long.MAX_VALUE) {
					RangeEdge nextRangeEdge = getNextRangeEdge(position);
					position = nextRangeEdge.position;
					inRangeDeepth += nextRangeEdge.inRangesDelta;

					if (inRangeDeepth == 0 && !inRange && position != Long.MAX_VALUE) {
						validRangeIdCount += 1;
						System.err.println("Single range: " + lastPosition + " - " + position + ", validRangeIdCount: " + validRangeIdCount);

					} else if (inRangeDeepth == 0 && inRange) {
						validRangeIdCount += position - lastPosition + 1;
						inRange = false;
						System.out.println("Exiting ranges: " + lastPosition + " - " + position + ", validRangeIdCount: " + validRangeIdCount);
					
					} else if (inRangeDeepth > 0 && !inRange) {
						lastPosition = position;
						inRange = true;
						System.out.println("Entering ranges: " + lastPosition + " - " + position + ", inRanges: " + inRangeDeepth);
					
					} else if (inRangeDeepth > 0 && inRange) {
						System.out.println("Chaning range deepth: " + position + ", inRange: " + inRange + ", inRanges: " + inRangeDeepth);
					}

				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private static class RangeEdge {
			private long position;
			private long inRangesDelta;

			RangeEdge(long position, long inRangesDelta) {
				this.position = position;
				this.inRangesDelta = inRangesDelta;
			}
		}

		private RangeEdge getNextRangeEdge(long start) {
			long nextRangeFrom = nextRangeFrom(start);
			long nextRangeTo = nextRangeTo(start);

			if (nextRangeFrom < nextRangeTo) {
				return new RangeEdge(nextRangeFrom, ranges.stream().filter(r -> r.from == nextRangeFrom).count());

			} else if (nextRangeTo < nextRangeFrom) {
				return new RangeEdge(nextRangeTo, 0 - ranges.stream().filter(r -> r.to == nextRangeTo).count());

			} else {
				System.err.println("Overlapping range edges at position: " + nextRangeFrom);
				return new RangeEdge(nextRangeFrom, ranges.stream().filter(r -> r.from == nextRangeFrom).count()
						- ranges.stream().filter(r -> r.to == nextRangeTo).count());
			}
		}

		private long nextRangeFrom(long start) {
			return ranges.stream().mapToLong(r -> r.from).filter(from -> from > start).min().orElse(Long.MAX_VALUE);
		}

		private long nextRangeTo(long start) {
			return ranges.stream().mapToLong(r -> r.to).filter(to -> to > start).min().orElse(Long.MAX_VALUE);
		}

		private long part1() {
			return validIdCount;
		}

		private long part2() {
			return validRangeIdCount;
		}

		private class IdRange {
			long from;
			long to;

			IdRange(long from, long to) {
				this.from = from;
				this.to = to;
			}

			private boolean contains(long id) {
				return id >= from && id <= to;
			}
		}
	}
}
