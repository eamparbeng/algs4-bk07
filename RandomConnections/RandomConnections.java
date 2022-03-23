import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

public class RandomConnections {
    public static void drawRandomConnections(int n, double p) {
        double center = 0.5;
        double dotRadius = 0.05;
        double widerRadius = center - dotRadius;
        double dotInterval = 360.0 / n;
        double[][] dotPoints = new double[n + 1][2];
        int i = 0;
        for (double angle = 0; angle <= 360; angle += dotInterval, i++) {
            double angleadians = Math.toRadians(angle);
            double x = center + widerRadius * Math.cos(angleadians);
            double y = center + widerRadius * Math.sin(angleadians);
            dotPoints[i][0] = x;
            dotPoints[i][1] = y;
        }

        for (i = 0; i < n + 1; i++) {
            StdDraw.circle(dotPoints[i][0], dotPoints[i][1], dotRadius);
        }

        for (i = 0; i < n + 1; i++) {
            for (int k = 0; k < n + 1; k++) {
                if(StdRandom.bernoulli(p)){
                    StdDraw.line(dotPoints[i][0], dotPoints[i][1], dotPoints[k][0], dotPoints[k][1]);
                }
            }
        }
    }

    public static void main(String[] args) {
        drawRandomConnections(10, 0.4);
    }
}