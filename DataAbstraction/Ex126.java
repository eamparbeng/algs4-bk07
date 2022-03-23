import edu.princeton.cs.algs4.StdOut;

public class Ex126 {
    public static void main(String[] args) {
        if (args.length < 2)
            StdOut.println("Wrong number of arguments");

        String word1 = args[0];
        String word2 = args[1];

        if (word1.length() == word2.length()
                && (word2 + word2).indexOf(word1) > 0) {
            StdOut.printf("%s is circular rotation of %s\n", word1, word2);
        } else {
            StdOut.printf("%s is not circular rotation of %s\n", word1, word2);
        }
    }
}