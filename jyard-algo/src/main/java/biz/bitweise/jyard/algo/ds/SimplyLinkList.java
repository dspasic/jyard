package biz.bitweise.jyard.algo.ds;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

// Not thread safe
public class SimplyLinkList<T> implements Iterable<T> {

  private Node<T> head;

  private static class Node<T> {
    private T value;
    private Node<T> next;
  }

  public SimplyLinkList() {}

  @Nullable
  public T getFirst() {
    return head == null ? null : head.value;
  }

  public void add(T value) {
    Node<T> n = new Node<>();
    n.value = value;
    n.next = head;
    head = n;
  }

  // Add sorted note
  // https://www.youtube.com/watch?v=2ZLl8GAk1X4&t=25071s
  public void addSorted(T value) {
    final Node<T> n = new Node<>();
    n.value = value;

    if (head == null) {
      add(value);
      return;
    }

    if (!(value instanceof Comparable)) {
      throw new ClassCastException();
    }

    @SuppressWarnings("unchecked")
    var v = (Comparable<T>) value;
    var current = head;
    Node<T> temp = current;

    while (current != null && v.compareTo(current.value) > 0) {
      temp = current;
      current = current.next;
    }
    n.next = current;
    temp.next = n;
  }

  public T removeFirst() {
    if (head == null) {
      return null;
    }
    final var node = head;
    head = head.next;
    return node.value;
  }

  /**
   * https://www.youtube.com/watch?v=2ZLl8GAk1X4&list=PLEn5OGbcdoM_kkzwluLHx0i4vEoSF2Pb3&index=6&t=22962s
   */
  public void reverse() {
    Node<T> nextCurrent;
    Node<T> reversed = null;
    Node<T> current = head;
    while (current != null) {
      nextCurrent = current.next;
      current.next = reversed;
      reversed = current;
      current = nextCurrent;
    }
    head = reversed;
  }

  /**
   * https://www.youtube.com/watch?v=2ZLl8GAk1X4&list=PLEn5OGbcdoM_kkzwluLHx0i4vEoSF2Pb3&index=6&t=22962s
   */
  public void removeDuplicates() {
    if (size() < 2) {
      return;
    }

    var current = head.next;
    while (current != null && current.next != null) {
      if (current.value.equals(current.next.value)) {
        current.next = current.next.next;
      } else {
        current = current.next;
      }
    }
  }

  public boolean isEmpty() {
    return head == null;
  }

  public T findNFromEnd(int n) {
    if (n < 0 || n > size()) {
      throw new IndexOutOfBoundsException("n must be in range [0.." + size() + "]");
    }

    if (head == null) {
      return null;
    }

    if (n == 0) {
      return head.value;
    }

    var refNode = head;
    var nthNode = head;
    int count = 0;

    while (count < n) {
      refNode = refNode.next;
      count++;
    }

    while (refNode != null) {
      refNode = refNode.next;
      nthNode = nthNode.next;
    }

    return nthNode.value;
  }

  public boolean contains(T v) {
    Objects.requireNonNull(v);

    if (head == null) {
      return false;
    }
    var current = head;
    while (current != null) {
      if (current.value.equals(v)) {
        return true;
      }
      current = current.next;
    }
    return false;
  }

  public int size() {
    Node<T> current = head;
    int size = 0;
    while (current != null) {
      size++;
      current = current.next;
    }
    return size;
  }

  @Override
  @Nonnull
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return head != null && head.next != null;
      }

      @Override
      public T next() {
        return head.next.value;
      }
    };
  }

  public void printList() {
    Node<T> current = head;
    while (current != null) {
      System.out.print(current.value + " --> ");
      current = current.next;
    }
    System.out.println("null");
  }

  public static void main(String[] args) {
    final var l = new SimplyLinkList<Integer>();
    l.add(3);
    l.add(4);
    l.add(3);
    l.add(5);
    l.add(9);
    l.add(234);
    l.printList();

    System.out.println(l.size());
  }
}
