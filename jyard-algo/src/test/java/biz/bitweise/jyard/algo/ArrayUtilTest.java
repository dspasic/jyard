package biz.bitweise.jyard.algo;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilTest {

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
    int[] numbers = new int[] {1, 2, 5, 6, 2, 3, 1, 6, 0, -10, 9};
    assertEquals(-10, ArrayUtil.findMin(numbers));
  }

  private static void printArray(int[] a) {
    System.out.println(
        Arrays.stream(a)
            .mapToObj(i -> Integer.valueOf(i).toString())
            .collect(Collectors.joining(",")));
  }

  @Test
  void findSecondMaximum() {
    assertEquals(1, ArrayUtil.findSecondMaximum(new int[] {1}));
    assertEquals(1, ArrayUtil.findSecondMaximum(new int[] {1, 3}));
    assertEquals(3, ArrayUtil.findSecondMaximum(new int[] {1, 3, 5}));
    assertEquals(4, ArrayUtil.findSecondMaximum(new int[] {1, 3, 5, 4}));
    assertEquals(4, ArrayUtil.findSecondMaximum(new int[] {1, 3, 5, 4, 5}));
  }

  @Test
  void moveZerosToTail() {
    var numbers = new int[] {1};
    ArrayUtil.moveZeroToTail(numbers);
    assertArrayEquals(new int[] {1}, numbers);

    numbers = new int[] {0, 1, 0, 4, 12};
    ArrayUtil.moveZeroToTail(numbers);
    assertArrayEquals(new int[] {1, 4, 12, 0, 0}, numbers);

    numbers = new int[] {8, 1, 0, 2, 1, 0, 3};
    ArrayUtil.moveZeroToTail(numbers);
    assertArrayEquals(new int[] {8, 1, 2, 1, 3, 0, 0}, numbers);
  }

  @Test
  void resizeArray() {
    final var initialSize = new int[] {1, 4, 5, 7};
    assertEquals(4, initialSize.length);

    final var resized = ArrayUtil.resize(initialSize, 10);
    assertEquals(10, resized.length);

    System.out.println(resized);
  }

  @Test
  void findMissingNumber() {
    final var n = new int[] {2, 4, 1, 8, 6, 3, 7};
    assertEquals(5, ArrayUtil.findMissingNumber(n));
  }
}
