package biz.bitweise.jyard.algo.ds;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Iterator;

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

  public T removeFirst() {
    if (head == null) {
      return null;
    }
    final var node = head;
    head = head.next;
    return node.value;
  }

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

  public boolean isEmpty() {
    return head == null;
  }

  public boolean contains(T v) {
    if (v == null) {
      throw new IllegalArgumentException("v must be not null");
    }
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
