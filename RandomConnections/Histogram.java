import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

public class Histogram {
    public static void doHistogram(int n, double lo, double hi) {
        double delta = (hi - lo) / n;
        int[] histogramData = new int[n];
        double[][] boundsData = new double[n][2];
        for (int i = 0; i < n; i++) {
            boundsData[i][0] = lo + i * delta;
            boundsData[i][1] = lo + (i + 1) * delta;
            histogramData[i] = 0;
        }

        while (!StdIn.isEmpty()) {
            double currentElement = StdIn.readDouble();
            for (int i = 0; i < n; i++) {
                if (currentElement >= boundsData[i][0] && currentElement < boundsData[i][1]) {
                    histogramData[i]++;
                }
            }
        }

        double maxHistogramHeight = 0;
        for (int i = 0; i < n; i++) {
            if (histogramData[i] > maxHistogramHeight) {
                maxHistogramHeight = histogramData[i];
            }
        }

        StdDraw.setXscale(lo, hi);
        StdDraw.setYscale(-1, maxHistogramHeight + 1);
        for (int i = 0; i < n; i++) {
            double halfWidth = delta/2;
            double halfHeight = histogramData[i] / 2; 
            StdDraw.filledRectangle(boundsData[i][0]+ halfWidth, halfHeight, halfWidth, halfHeight); 
        }

    }

    public static void main(String[] args) {
        doHistogram(10, 10, 20);
    }
}
