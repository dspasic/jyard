package biz.bitweise.jyard.algo;

import java.util.Arrays;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest {

  @Test
  void removeEvenIntegersFromArray() {
    int[] numbers = IntStream.range(0, 130).toArray();
    int[] odds = ArrayUtil.removeEvenNumbers(numbers);
    assertEquals(65, odds.length);
    printArray(odds);
  }

  private static void printArray(int[] a) {
    Arrays.stream(a).forEach(System.out::println);
  }
}
