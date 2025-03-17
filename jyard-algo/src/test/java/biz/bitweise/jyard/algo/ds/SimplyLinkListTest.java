package biz.bitweise.jyard.algo.ds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimplyLinkListTest {

  @Test
  void givenEmptyList_whenAddTwoElements_thenCountTwo() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(2);

    assertEquals(2, list.size());
  }

  @Test
  void givenTwoElement_whenRemoveFirst_thenCountOne() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(5);

    assertEquals(2, list.size());

    final var n = list.removeFirst();
    assertEquals(5, n);

    assertEquals(1, list.size());
  }
}
