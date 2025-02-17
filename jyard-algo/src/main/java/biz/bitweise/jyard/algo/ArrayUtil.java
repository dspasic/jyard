package biz.bitweise.jyard.algo;

public class ArrayUtil {

  public static int[] removeEvenNumbers(final int[] ints) {
    var oddCount = 0;
    for (var c: ints) {
      if (isOdd(c)) oddCount++;
    }

    if (oddCount == 0) {
      return new int[0];
    }

    int[] odds = new int[oddCount];
    var idx = 0;
    for (var c: ints) {
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

}
