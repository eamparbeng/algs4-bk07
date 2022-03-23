import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_INTERVAL_CONSTANT = 1.96;

    private final double[] fractionOfOpenSites;
    private final int numberOfTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("n must be an integer larger than 0");
        }

        numberOfTrials = trials;
        double gridSize = n * n;

        fractionOfOpenSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            // StdOut.printf("Trial %d of %d", i, n);
            Percolation percolation = new Percolation(n);
            doTrial(n, percolation);
            // We have percolation
            fractionOfOpenSites[i] = percolation.numberOfOpenSites() / gridSize;
        }
    }

    private void doTrial(int n, Percolation percolation) {
        while (!percolation.percolates()) {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            if (!percolation.isOpen(randomRow, randomCol)) {
                percolation.open(randomRow, randomCol);
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionOfOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double xbar = mean();
        double s = stddev();
        return xbar - (CONFIDENCE_INTERVAL_CONSTANT * s / Math.sqrt(numberOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double xbar = mean();
        double s = stddev();
        return xbar + (CONFIDENCE_INTERVAL_CONSTANT * s / Math.sqrt(numberOfTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n;
        int trials;
        if (args.length > 1) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } else {
            n = StdIn.readInt();
            trials = StdIn.readInt();
        }
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }

}
