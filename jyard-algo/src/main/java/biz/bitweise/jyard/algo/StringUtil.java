package biz.bitweise.jyard.algo;

import java.util.Objects;

public final class StringUtil {
  public static boolean isPalindrome(String s) {
    Objects.requireNonNull(s);
    if (s.isEmpty()) return true;
    if (s.length() == 1) return true;
    int l = 0;
    int r = s.length() - 1;
    do {
      if (s.charAt(l) != s.charAt(r)) {
        return false;
      }
    } while (l++ < r--);
    return true;
  }
}
