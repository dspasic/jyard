package biz.bitweise.jyard.algo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

  @Test
  void isPalindrome() {
    assertTrue(StringUtil.isPalindrome("a"), "a must be a palindrome");
    assertFalse(StringUtil.isPalindrome("Dejan"), "Dejan must not be a palindrome");
    assertTrue(StringUtil.isPalindrome("anna"), "anna must be a palindrome");
    assertTrue(StringUtil.isPalindrome("madam"), "madam must be a palindrome");
  }
}
