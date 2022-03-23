import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        String currentKing = "", currentWord = "";
        int loopCounter = 0;
        while (!StdIn.isEmpty()) {
            currentWord = StdIn.readString();
            // StdOut.print(currentWord);
            if (StdRandom.bernoulli(1.0 / ++loopCounter))
                currentKing = currentWord;
        }
        StdOut.print(currentKing);
    }
}
