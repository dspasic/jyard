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

  @Test
  void givenTwoElements_whenSearchGivenElement_thenReturnTrue() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(5);

    assertTrue(list.contains(5));
  }

  @Test
  void givenTwoElements_whenSearchNonGivenElement_thenReturnFalse() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(5);

    assertFalse(list.contains(0));
  }

  @Test
  void givenFourElements_whenReverseList_thenReverse() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(10);
    list.add(8);
    list.add(1);
    list.add(11);
    list.reverse();

    assertEquals(10, list.getFirst());
  }
}
