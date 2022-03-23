import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex121 {
    public static void main(String[] args) {
        if (args.length == 0)
            StdOut.println("No arguments");

        int n = Integer.parseInt(args[0]);
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            Point2D point = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));
            points[i] = point;
        }

        double minDistance = 1;
        for(int i=0; i<n; i++){
            for(int j=0; j<n;j++){
                if(i!=j){
                    double distance = points[i].distanceTo(points[j]);
                    if(distance<minDistance){
                        minDistance = distance;
                    }
                }
            }
        }

        StdOut.printf("Min Distance %f\n", minDistance);
    }
}