
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) { // compare two points by y-coordinates, breaking ties by x-coordinates
        if (that == null)
            throw new NullPointerException("Argument should not be null");

        if (this.y < that.y)
            return -1;
        else if (this.y == that.y)
            return Integer.compare(this.x, that.x);
        else
            return 1;
    }

    public double slopeTo(Point that) { // the slope between this point and that point
        if (that == null)
            throw new NullPointerException("Argument should not be null");

        if ((this.x == that.x) && (this.y == that.y))
            return Double.NEGATIVE_INFINITY;
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        if (this.y == that.y)
            return +0.0;
        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() { // compare two points by slopes they make with this point
        return new BySlope(this);
    }

    private static class BySlope implements Comparator<Point> {
        private final Point invokingPoint;

        private BySlope(Point invokingPoint) {
            this.invokingPoint = invokingPoint;
        }

        public int compare(Point v, Point w) {
            return Double.compare(invokingPoint.slopeTo(v), invokingPoint.slopeTo(w));
        }
    }
}