import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Triton on 2017/11/30.
 */
public class PercolationStats {
    private static final double CONFIDENCE_MULTIPLIER = 1.96;

    private final int n;
    private final int trials;
    private double[] thresholds;

    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials must be positive: " + n + ", " + trials);
        }

        this.n = n;
        this.trials = trials;
        this.thresholds = new double[trials];

        runMonteCarlo();
        computeStats();
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLow;
    }

    public double confidenceHi() {
        return confidenceHigh;
    }

    private void runMonteCarlo() {
        for (int i = 0; i < trials; ++i) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }

            int openCount = percolation.numberOfOpenSites();
            thresholds[i] = (double) openCount / (n * n);
        }
    }

    private void computeStats() {
        mean = StdStats.mean(thresholds);
        stddev = trials > 1 ? StdStats.stddev(thresholds) : Double.NaN;

        double range = CONFIDENCE_MULTIPLIER * stddev / Math.sqrt(trials);
        confidenceLow = mean - range;
        confidenceHigh = mean + range;
    }

    private void printStats() {
        StdOut.printf("%-24s = %.18f\n", "mean", mean);
        StdOut.printf("%-24s = %.18f\n", "stddev", stddev);
        StdOut.printf("%-24s = [%.18f, %.18f]\n", "95% confidence interval", confidenceLow, confidenceHigh);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        stats.printStats();
    }
}
