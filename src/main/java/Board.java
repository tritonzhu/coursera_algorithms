import java.util.ArrayList;
import java.util.List;

/**
 * Created by Triton on 2019/4/20.
 */
public class Board {
    private final static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int[][] blocks;
    private int n;

    private int hammingDistance = 0;
    private int manhattanDistance = 0;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.n = blocks.length;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (blocks[i][j] > 0) {
                    if (i * n + j + 1 != blocks[i][j]) {
                        ++hammingDistance;
                    }

                    int expectX = (blocks[i][j] - 1) / n;
                    int expectY = (blocks[i][j] - 1) % n;
                    manhattanDistance += Math.abs(i - expectX) + Math.abs(j - expectY);
                }
            }
        }
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        return this.hammingDistance;
    }

    public int manhattan() {
        return this.manhattanDistance;
    }

    public boolean isGoal() {
        return this.hammingDistance == 0 || this.manhattanDistance == 0;
    }

    public Board twin() {
        int[][] grid = new int[n][n];
        int[] first = null;
        int[] second = null;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                grid[i][j] = blocks[i][j];
                if (grid[i][j] != 0) {
                    if (first == null) {
                        first = new int[2];
                        first[0] = i;
                        first[1] = j;
                    }
                    else if (second == null) {
                        second = new int[2];
                        second[0] = i;
                        second[1] = j;
                    }
                }
            }
        }

        swap(grid, first, second);
        return new Board(grid);
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (n != other.n) {
            return false;
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<>();
        int[] zeroIndex = new int[2];
        outer: for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (blocks[i][j] == 0) {
                    zeroIndex[0] = i;
                    zeroIndex[1] = j;
                    break outer;
                }
            }
        }

        for (int[] dir : dirs) {
            int x = zeroIndex[0] + dir[0];
            int y = zeroIndex[1] + dir[1];

            if (x >= 0 && x < n && y >= 0 && y < n) {
                int[] pos = new int[]{x, y};
                int[][] grid = copy();
                swap(grid, pos, zeroIndex);
                list.add(new Board(grid));
            }
        }

        return list;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n);
        builder.append("\n");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                builder.append(blocks[i][j]);
                builder.append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private int[][] copy() {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                grid[i][j] = blocks[i][j];
            }
        }

        return grid;
    }

    private void swap(int[][] grid, int[] i, int[] j) {
        int tmp = grid[i[0]][i[1]];
        grid[i[0]][i[1]] = grid[j[0]][j[1]];
        grid[j[0]][j[1]] = tmp;
    }

    public static void main(String[] args) {
        int[][] grid = new int[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                grid[i][j] = i * 3 + j + 1;
            }
        }

        grid[2][2] = 0;

        Board board = new Board(grid);
        System.out.println(board.toString());
    }
}
