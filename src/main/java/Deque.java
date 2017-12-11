import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Triton on 2017/12/9.
 */
public class Deque<Item> implements Iterable<Item> {
    private Item[] array;
    private int capacity;
    private int size;
    private int head;
    private int tail;

    public Deque() {
        capacity = 2;
        array = (Item[]) (new Object[capacity]);
        size = 0;
        head = capacity / 2;
        tail = head;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed to add");
        }

        if (head == 0) {
            resize(capacity * 2);
        }
        array[--head] = item;
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed to add");
        }

        if (tail == capacity - 1) {
            resize(capacity * 2);
        }
        array[++tail] = item;
        size += 1;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }

        Item first = array[head++];
        size -= 1;
        if (size < capacity / 4) {
            resize(capacity / 2);
        }
        return first;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }

        Item last = array[tail--];
        size -= 1;
        if (size < capacity / 4) {
            resize(capacity / 2);
        }
        return last;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void resize(int newCapacity) {
        Item[] copy = (Item[]) (new Object[newCapacity]);
        int newHead = newCapacity / 2 - size / 2;
        for (int i = 0; i < size; ++i) {
            copy[i + newHead] = array[head + i];
            array[head + i] = null;
        }
        array = copy;
        head = newHead;
        tail = head + size - 1;
        capacity = newCapacity;
    }

    private class DequeIterator implements Iterator<Item> {
        private int current = head;

        @Override
        public boolean hasNext() {
            return current <= tail;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more items");
            }
            return array[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.removeFirst();
        deque.removeLast();
    }
}
