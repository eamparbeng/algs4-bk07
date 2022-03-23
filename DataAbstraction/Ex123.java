import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex123 {
    public static void main(String[] args) {
        if (args.length < 3)
            StdOut.println("No/Invalid arguments");

        int n = Integer.parseInt(args[0]);
        double min = Double.parseDouble(args[1]);
        double max = Double.parseDouble(args[2]);
        Interval2D[] intervals = new Interval2D[n];
        for (int i = 0; i < n; i++) {
            double x1 = StdRandom.uniform(min, max);
            double x2 = StdRandom.uniform(min, max);
            double y1 = StdRandom.uniform(min, max);
            double y2 = StdRandom.uniform(min, max);
            if (x1 > y1) {
                double t = x1;
                x1 = y1;
                y1 = t;
            }
            if (x2 > y2) {
                double t = x2;
                x2 = y2;
                y2 = t;
            }
            Interval1D width = new Interval1D(x1, y1);
            Interval1D height = new Interval1D(x2, y2);
            Interval2D point = new Interval2D(width, height);
            intervals[i] = point;
        }

        StdDraw.setScale(min, max);
        for (int i = 0; i < n; i++) {
            Interval2D interval = intervals[i];
            interval.draw();
        }

        int intersectCount = 0;
        for (int i = 0; i < n-1; i++) {
            for (int j = i + 1; j < n; j++) {
                Interval2D int1 = intervals[i];
                Interval2D int2 = intervals[j];
                if (int1.intersects(int2)) {
                    intersectCount++;
                    StdOut.printf("%s intersects %s\n", int1.toString(), int2.toString());
                }
            }
        }
        StdOut.printf("Intersection Count %d\n", intersectCount);

    }
} 