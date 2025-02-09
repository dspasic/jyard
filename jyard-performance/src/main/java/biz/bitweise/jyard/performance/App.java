package biz.bitweise.jyard.performance;

import org.jetbrains.annotations.*;

public class App {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    final var app = new App();
  }

  /**
   * This method has a NotNull annotation on the argument.
   *
   * <p>The test is about to see if the JVM will check automatically for {@code null} values.
   * Spoiler: it does not.
   */
  public void argumentWithAnnotation(@NotNull final String name) {
    System.out.printf((name) + "%n");
  }

  /**
   * testing allMatch on an empty stream.
   *
   * <p>What is the result of allMatch on an empty stream? spoiler: it is true.
   */
  public void emptyStreamAndAllMatch() {
    final var empty = new String[0];
    var allMatch = java.util.Arrays.stream(empty).allMatch(s -> s.equals("foo"));

    System.out.println("" + allMatch);
  }
}
