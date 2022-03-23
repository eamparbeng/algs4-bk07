import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int first = 0;
    private int last = 0;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (last == q.length) {
            resize(2 * q.length);
        }
        q[last++] = item;
        ++N;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int i = 0;
        for (; i < last - first; i++) {
            copy[i] = q[first + i];
        }
        q = copy;
        first = 0;
        last = i;
    }

    // remove and return a random item
    public Item dequeue() {
        if (last == first) {
            throw new NoSuchElementException("Queue empty");
        }

        Item item = q[first++];
        q[first - 1] = null;

        if (last > 0 && last == q.length / 4)
            resize(q.length / 2);
        --N;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (last == first) {
            throw new NoSuchElementException("Queue empty");
        }

        int index = StdRandom.uniform(first, last);
        return q[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currentIndex = first;
        private int[] indexes;

        RandomizedQueueIterator() {
            indexes = StdRandom.permutation(last, N);
        }

        public boolean hasNext() {
            return currentIndex < last;
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation not supported");
        }

        public Item next() {
            if (currentIndex == last) {
                throw new NoSuchElementException("Deque empty");
            }
            Item item = q[indexes[currentIndex++] + first];

            return item;
        }
    }
}