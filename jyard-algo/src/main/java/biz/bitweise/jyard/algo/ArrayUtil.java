package biz.bitweise.jyard.algo;

import java.util.Objects;

public class ArrayUtil {

  public static int[] removeEvenNumbers(final int[] ints) {
    var oddCount = 0;
    for (var c : ints) {
      if (isOdd(c)) oddCount++;
    }

    if (oddCount == 0) {
      return new int[0];
    }

    int[] odds = new int[oddCount];
    var idx = 0;
    for (var c : ints) {
      if (isOdd(c)) odds[idx++] = c;
    }

    return odds;
  }

  private static boolean isOdd(int n) {
    return (n & 1) == 1;
  }

  // this example it copies the array
  public static int[] reverse(final int[] numbers) {
    int[] reversed = new int[numbers.length];
    for (int i = 0, j = numbers.length - 1; i < numbers.length; i++, j--) {
      reversed[j] = numbers[i];
    }
    return reversed;
  }

  // mutates the given array
  public static void reverseArray(final int[] numbers) {
    if (numbers == null || numbers.length == 0) {
      return;
    }

    int start = 0;
    int end = numbers.length - 1;

    while (start < end) {
      final var tmp = numbers[start];
      numbers[start] = numbers[end];
      numbers[end] = tmp;
      start++;
      end--;
    }
  }

  public static int findMin(final int[] numbers) {
    int min = Integer.MAX_VALUE;
    for (int number : numbers) {
      if (number < min) min = number;
    }
    return min;
  }

  public static int findSecondMaximum(final int[] numbers) {
    if (numbers == null || numbers.length == 0) {
      throw new IllegalArgumentException(
          "numbers must not be null or must have at least one element");
    }

    if (numbers.length == 1) {
      return numbers[0];
    }

    int max = Integer.MIN_VALUE;
    int secondMax = Integer.MIN_VALUE;

    for (int n : numbers) {
      if (n > max) {
        secondMax = max;
        max = n;
      }
      if (n > secondMax && n < max) {
        secondMax = n;
      }
    }

    return secondMax;
  }

  public static void moveZeroToTail(final int[] numbers) {
    if (numbers.length < 2) {
      return;
    }

    int zero = 0;
    int ptr = 0;

    while (ptr < numbers.length) {
      if (numbers[ptr] != 0 && numbers[zero] == 0) {
        int tmp = numbers[zero];
        numbers[zero] = numbers[ptr];
        numbers[ptr] = tmp;
      }

      if (numbers[zero] != 0) {
        zero++;
      }

      ptr++;
    }
  }

  public static int[] resize(final int[] numbers, final int newSize) {
    Objects.requireNonNull(numbers);

    if (numbers.length == newSize) {
      return numbers;
    }

    final var resized = new int[newSize];
    for (int i = 0; i < newSize; i++) {
      if (i < numbers.length) {
        resized[i] = numbers[i];
      }
    }

    return resized;
  }

  /**
   * Given an array of n - 1 distinct numbers in a range of 1 to n. Find the missing numbers in it.
   */
  public static int findMissingNumber(final int[] numbers) {
    // sum the numbers from 1 to n
    // math formula for the sum of first natural numbers is n(n+1)/2
    final int n = numbers.length + 1;
    int m = n * (n + 1) / 2;
    for (int i = 0; i < numbers.length; i++) {
      m -= numbers[i];
    }
    return m;
  }
}
