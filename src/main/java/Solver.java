import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Triton on 2019/4/20.
 */
public class Solver {
    private class Node implements Comparable<Node> {
        Node parent;
        Board board;
        int move;
        int priority;

        Node(Node parent, Board board, int move) {
            this.parent = parent;
            this.board = board;
            this.move = move;
            this.priority = board.manhattan() + move;
        }

        @Override
        public int compareTo(Node other) {
            if (priority != other.priority) {
                return priority - other.priority;
            }
            return board.hamming() - other.board.hamming();
        }
    }

    private Node start;
    private Node twinStart;
    private boolean isSolvable;
    private LinkedList<Board> path = new LinkedList<>();

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null board");
        }

        this.start = new Node(null, initial, 0);
        this.twinStart = new Node(null, initial.twin(), 0);
        Node finalNode = solve();
        this.isSolvable = finalNode != null;

        Node node = finalNode;
        while (node != null) {
            path.addFirst(node.board);
            node = node.parent;
        }
    }

    public boolean isSolvable() {
        return this.isSolvable;
    }

    public int moves() {
        return path.isEmpty() ? -1 : path.size() - 1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        return path;
    }

    private Node solve() {
        MinPQ<Node> queue = new MinPQ<>();
        queue.insert(start);

        MinPQ<Node> twinQueue = new MinPQ<>();
        twinQueue.insert(twinStart);

        while (true) {
            Node node = search(queue);
            if (node.board.isGoal()) {
                return node;
            }

            Node twinNode = search(twinQueue);
            if (twinNode.board.isGoal()) {
                return null;
            }
        }

    }

    private Node search(MinPQ<Node> queue) {
        Node node = queue.delMin();

        Iterator<Board> iter = node.board.neighbors().iterator();
        while (iter.hasNext()) {
            Board board = iter.next();
            if (node.parent == null || !board.equals(node.parent.board)) {
                queue.insert(new Node(node, board, node.move + 1));
            }
        }

        return node;
    }

    public static void main(String[] args) {
    }
}
