package biz.bitweise.jyard.algo;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest {

  @Test
  void removeEvenIntegersFromArray() {
    int[] numbers = IntStream.range(0, 12).toArray();
    int[] odds = ArrayUtil.removeEvenNumbers(numbers);
    assertEquals(6, odds.length);
    printArray(odds);
  }

  @Test
  void reverseNumbers() {
    int[] numbers = IntStream.range(0, 5).toArray();
    int[] reversed = ArrayUtil.reverse(numbers);

    assertEquals(numbers.length, reversed.length);

    printArray(numbers);
    printArray(reversed);
  }

  @Test
  void reverseArrayAsReference() {
    int[] numbers = IntStream.range(0, 5).toArray();

    ArrayUtil.reverseArray(numbers);
    printArray(numbers);

    assertEquals(5, numbers.length);
    assertEquals(4, numbers[0]);
  }

  @Test
  void findMinValue() {
    int[] numbers = new int[]{1, 2, 5, 6, 2, 3, 1, 6, 0, -10, 9};
    assertEquals(-10, ArrayUtil.findMin(numbers));
  }

  private static void printArray(int[] a) {
    System.out.println(
        Arrays.stream(a)
            .mapToObj(i -> Integer.valueOf(i).toString())
            .collect(Collectors.joining(",")));
  }
}
