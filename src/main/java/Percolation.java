import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Triton on 2017/11/30.
 */
public class Percolation {
    private final int n;
    private final int size;
    private boolean[] area;
    private final WeightedQuickUnionUF union;
    private int openCount = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is not positive: " + n);
        }

        this.n = n;
        this.size = n * n + 2; // plus two virtual nodes
        this.area = new boolean[size];
        this.union = new WeightedQuickUnionUF(size);
    }

    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            int index = xy2index(row, col);
            area[index] = true;
            ++openCount;

            // connecting top virtual node if it's in the top line
            if (row == 1) {
                union.union(index, 0);
            }
            // connecting bottom virtual node if it's the in the bottom line and the percolation is not percolated yet
            if (row == n && !percolates()) {
                union.union(index, size - 1);
            }

            // connecting open neighbors
            // top
            if (row > 1 && isOpen(row - 1, col)) {
                union.union(index, xy2index(row - 1, col));
            }
            // left
            if (col > 1 && isOpen(row, col - 1)) {
                union.union(index, xy2index(row, col - 1));
            }
            // right
            if (col < n && isOpen(row, col + 1)) {
                union.union(index, xy2index(row, col + 1));
            }
            // bottom
            if (row < n && isOpen(row + 1, col)) {
                union.union(index, xy2index(row + 1, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return area[xy2index(row, col)];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return union.connected(0, xy2index(row, col));
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return union.connected(0, size - 1);
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("invalid row or column: " + row + ", " + col);
        }
    }

    private int xy2index(int row, int col) {
        // didn't minus 1 from col since the first element is the virtual parent of the first line
        return (row - 1) * n + col;
    }
}
