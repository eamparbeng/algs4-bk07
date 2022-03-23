import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] pointsArg) { // finds all line segments containing 4 points
        if (pointsArg == null)
            throw new IllegalArgumentException("points array should not be null");

        Point[] pointsCopy = Arrays.copyOf(pointsArg, pointsArg.length);
        Arrays.sort(pointsCopy);
        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0)
                throw new IllegalArgumentException("Input contains duplicate.");
        }

        lineSegments = new ArrayList<LineSegment>();
        determineSegments(pointsCopy);
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() { // the line segments
        return lineSegments.toArray(new LineSegment[0]);
    }

    private void determineSegments(Point[] pointsCopy) {
        for (int i = 0; i < pointsCopy.length - 3; i++)
            for (int j = i + 1; j < pointsCopy.length - 2; j++)
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k]))
                        for (int m = k + 1; m < pointsCopy.length; m++) {
                            Point[] tuple = new Point[] { pointsCopy[i], pointsCopy[j], pointsCopy[k], pointsCopy[m] };
                            Arrays.sort(tuple);
                            if (tuple[0].compareTo(tuple[1]) < 0) {
                                lineSegments.add(new LineSegment(pointsCopy[i], pointsCopy[m]));
                            }
                        }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}