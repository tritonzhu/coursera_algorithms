import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * Created by Owen on 2017/12/11.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        try {
            while (true) {
                queue.enqueue(StdIn.readString());
            }
        } catch (NoSuchElementException e) {
            // do nothing
        }

        for (int i = 0; i < k; ++i) {
            StdOut.println(queue.dequeue());
        }
    }
}
