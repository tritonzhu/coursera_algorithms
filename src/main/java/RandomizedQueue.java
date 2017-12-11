import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Owen on 2017/12/11.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int capacity;
    private int size;
    private int head;
    private int tail;

    public RandomizedQueue() {
        capacity = 1;
        array = (Item[]) (new Object[capacity]);
        size = 0;
        head = 0;
        tail = head;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed to add");
        }

        if (size == capacity) {
            resize(capacity * 2);
        }
        tail = (tail + 1) % capacity;
        array[tail] = item;
        size += 1;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }

        int index = (head + StdRandom.uniform(size)) % capacity;
        // swap the random chosen item and the last item
        Item item = array[index];
        array[index] = array[tail];

        tail = (tail - 1 + capacity) % capacity;
        size -= 1;
        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        int index = (head + StdRandom.uniform(size)) % capacity;
        return array[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int newCapacity) {
        Item[] copy = (Item[]) (new Object[newCapacity]);
        for (int i = 0; i < size; ++i) {
            int index = (i + head) % capacity;
            copy[i] = array[index];
            array[index] = null;
        }
        array = copy;
        head = 0;
        tail = head + size - 1;
        capacity = newCapacity;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randomArray;
        private int current;

        public RandomizedQueueIterator() {
            randomArray = (Item[]) (new Object[size]);
            for (int i = 0; i < size; ++i) {
                randomArray[i] = array[(i + head) % capacity];
            }
            StdRandom.shuffle(randomArray);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more items");
            }
            return randomArray[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static void main(String[] args) {

    }
}
