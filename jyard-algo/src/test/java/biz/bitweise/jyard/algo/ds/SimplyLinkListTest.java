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
  void givenTwoElements_whenRemoveFirst_thenCountOne() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(5);

    assertEquals(2, list.size());

    final var n = list.removeFirst();
    assertEquals(5, n);

    assertEquals(1, list.size());
  }

  @Test
  void givenTwoElements_whenSearchGivenElement_thenReturnFalse() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(5);
    list.add(2);

    assertEquals(3, list.size());

    assertFalse(list.contains(6));
  }

  @Test
  void givenTwoElements_whenSearchGivenElement_thenReturnTrue() {
    SimplyLinkList<Integer> list = new SimplyLinkList<>();
    list.add(1);
    list.add(5);
    list.add(2);

    assertEquals(3, list.size());

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

  // How to find nth node from the end of a Singly Linked List

  @Test
  void givenFiveElements_whenFindSecond_thenReturnThirdElement() {
    final var list = new SimplyLinkList<Integer>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);

    assertEquals(3, list.findNFromEnd(3));
  }

  @Test
  void givenTwoElements_whenFindThirdElement_thenThrowException() {
    final var list = new SimplyLinkList<Integer>();
    list.add(1);
    list.add(2);

    assertThrows(IndexOutOfBoundsException.class, () -> list.findNFromEnd(3));
  }

  @Test
  void giveTwoElements_whenFindSecondElement_thenReturnFirstElement() {
    final var list = new SimplyLinkList<Integer>();
    list.add(2);
    list.add(1);

    assertEquals(1, list.findNFromEnd(2));
  }

  @Test
  void giveTwoElements_whenFindFirstElement_thenReturnSecondElement() {
    final var list = new SimplyLinkList<Integer>();
    list.add(2);
    list.add(1);

    assertEquals(2, list.findNFromEnd(1));
  }

  @Test
  void giveTwoElements_whenFindZeroElement_thenReturnLastElement() {
    final var list = new SimplyLinkList<Integer>();
    list.add(2);
    list.add(1);

    assertEquals(1, list.findNFromEnd(0));
  }

  @Test
  void giveTwoElements_whenFindNegative_thenThrowException() {
    final var list = new SimplyLinkList<Integer>();
    list.add(2);
    list.add(1);

    assertThrows(IndexOutOfBoundsException.class, () -> list.findNFromEnd(-1));
  }

  //
  // Remove duplicates
  //
  // https://www.youtube.com/watch?v=2ZLl8GAk1X4&list=PLEn5OGbcdoM_kkzwluLHx0i4vEoSF2Pb3&index=6&t=22962s
  //

  @Test
  void givenSixElementsWithThreeDuplicates_whenRemoveDuplicates_thenCountThree() {
    final var list = new SimplyLinkList<Integer>();
    list.add(3);
    list.add(3);
    list.add(3);
    list.add(2);
    list.add(2);
    list.add(1);

    list.removeDuplicates();

    assertEquals(3, list.size());
  }

  //
  // Insert node in a sorted Singly Link List
  //
  // https://www.youtube.com/watch?v=2ZLl8GAk1X4&t=25071s
  //

  @Test
  void givenTwoSortedElements_whenAddElement_thenInsertSorted() {
    final var list = new SimplyLinkList<Integer>();
    list.addSorted(3);
    list.addSorted(1);
    list.addSorted(2);

    assertEquals(1, list.removeFirst());
    assertEquals(2, list.removeFirst());
    assertEquals(3, list.removeFirst());
  }
}
