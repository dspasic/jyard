package biz.bitweise.jyard.algo.ds;

// Not thread safe
public class SimplyLinkList<T> {

  private Node<T> head;

  private static class Node<T> {
    private T value;
    private Node<T> next;
  }

  public SimplyLinkList() {}

  public void add(T value) {
    Node<T> n = new Node<>();
    n.value = value;
    n.next = head;
    head = n;
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
