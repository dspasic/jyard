package biz.bitweise.jyard.algo;

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
    int start = 0, end = numbers.length - 1;
    while (start < end) {
      var tmp = numbers[start];
      numbers[start] = numbers[end];
      numbers[end] = tmp;
      start++;
      end--;
    }
  }

  public static int findMin(final int[] numbers) {
    int min = Integer.MAX_VALUE;
    for (int number : numbers) {
      if (min > number) {
        min = number;
      }
    }
    return min;
  }

  public static int findSecondMaximum(final int[] numbers) {
    if (numbers.length < 2) {
      return numbers[0];
    }

    if (numbers.length == 2) {
      return numbers[1] > numbers[0] ? numbers[0] : numbers[1];
    }

    int maximum = numbers[0] > numbers[1] ? numbers[0] : numbers[1];
    int secondMaximum = numbers[0] > numbers[1] ? numbers[1] : numbers[0];

    for (int i = 2; i < numbers.length; i++) {
      if (numbers[i] > maximum) {
        secondMaximum = maximum;
        maximum = numbers[i];
      } else if (numbers[i] > secondMaximum && numbers[i] < maximum) {
        secondMaximum = numbers[i];
      }
    }

    return secondMaximum;
  }
}
