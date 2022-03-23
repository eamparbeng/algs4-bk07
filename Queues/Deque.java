import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int N;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkItemArgumentNotNull(item);

        Node newNode = new Node();
        newNode.item = item;
        if (first == null) {
            last = newNode;
            first = last;
        } else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        N += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkItemArgumentNotNull(item);

        Node newNode = new Node();
        if (last == null) {
            last = newNode;
            first = last;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        N += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Deque Empty");
        }
        Node temp = first;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }

        Item removedItem = temp.item;
        temp = null;
        N -= 1;
        return removedItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (first == null) {
            throw new NoSuchElementException("Deque Empty");
        }
        Node temp = last;
        first = first.next;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }

        Item removedItem = temp.item;
        temp = null;
        N -= 1;
        return removedItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> d1 = new Deque<String>();
        Deque<String> d2 = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String element = StdIn.readString();
            d1.addFirst(element);
        }

        for (String element : d1) {
            d2.addLast(element);
            StdOut.println(element);
        }

        int itemCount = d1.size();
        for (int i = 0; i < itemCount; i++) {
            d1.removeFirst();
            d2.removeLast();
        }

        StdOut.printf("d1.size(): %s\n", d1.size());
        StdOut.printf("d2.size(): %s\n", d2.size());
    }

    private void checkItemArgumentNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation not supported");
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("Deque empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }
}
