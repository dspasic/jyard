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
}
