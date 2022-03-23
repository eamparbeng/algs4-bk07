import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(final Point[] pointsArg) {
        if (pointsArg == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < pointsArg.length; i++) {
            if (pointsArg[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] pointsCopy = Arrays.copyOf(pointsArg, pointsArg.length);
        Arrays.sort(pointsCopy);
        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0)
                throw new IllegalArgumentException("Input contains duplicate.");
        }

        pointsCopy = Arrays.copyOf(pointsArg, pointsArg.length);
        for (Point p : pointsArg) {
            Arrays.sort(pointsCopy, p.slopeOrder());
            double slope = p.slopeTo(pointsCopy[0]);
            int count = 1, i;
            for (i = 1; i < pointsCopy.length; i++) {
                if (p.slopeTo(pointsCopy[i]) == slope)
                    count++;
                else {
                    if (count >= 3) {
                        Arrays.sort(pointsCopy, i - count, i);
                        if (p.compareTo(pointsCopy[i - count]) < 0)
                            segments.add(new LineSegment(p, pointsCopy[i - 1]));
                    }
                    slope = p.slopeTo(pointsCopy[i]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(pointsCopy, i - count, i);
                if (p.compareTo(pointsCopy[i - count]) < 0)
                    segments.add(new LineSegment(p, pointsCopy[i - 1]));
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        String fileName = "input6.txt";
        if (args.length > 0)
            fileName = args[0];
        In in = new In(fileName);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
