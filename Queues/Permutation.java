import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Invalid command line arguments");
        }

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        //StdOut.println("\n\n\nHere are %d random elements from your input\n");
        int counter = 0;
        Iterator<String> iter = q.iterator();
        while (counter++ < k && iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}