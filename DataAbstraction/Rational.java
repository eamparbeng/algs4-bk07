import edu.princeton.cs.algs4.StdOut;

public class Rational {
    private final long numerator, denominator;

    public Rational(long numerator, long denominator) {
        long commonDenominator = gcd(numerator, denominator);
        this.numerator = numerator / commonDenominator;
        this.denominator = denominator / commonDenominator;
    }

    public Rational(String expression) {
        String[] exprParts = expression.split("/");
        if (exprParts.length != 2)
            throw new IllegalArgumentException();

        long numerator = Long.parseLong(exprParts[0]);
        long denominator = Long.parseLong(exprParts[1]);

        long commonDenominator = gcd(numerator, denominator);
        this.numerator = numerator / commonDenominator;
        this.denominator = denominator / commonDenominator;
    }

    public Rational plus(Rational that) {
        long newNumerator = this.numerator * that.denominator + that.numerator * this.denominator;
        long newDenominator = this.denominator * that.denominator;

        return new Rational(newNumerator, newDenominator);
    }

    public Rational minus(Rational that) {
        long newNumerator = this.numerator * that.denominator - that.numerator * this.denominator;
        long newDenominator = this.denominator * that.denominator;

        return new Rational(newNumerator, newDenominator);
    }

    public Rational times(Rational that) {
        long newNumerator = this.numerator * that.numerator;
        long newDenominator = this.denominator * that.denominator;

        return new Rational(newNumerator, newDenominator);
    }

    public Rational dividedBy(Rational that) {
        long newNumerator = this.numerator * that.denominator;
        long newDenominator = this.denominator * that.numerator;

        return new Rational(newNumerator, newDenominator);
    }

    public boolean equals(Rational that) {
        return this.numerator == that.numerator && this.denominator == that.denominator;
    }

    public String toString() {
        return "" + numerator + "/" + denominator;
    }

    private long gcd(long a, long b) {
        if (b == 0)
            return a;
        long r = a % b;
        return gcd(b, r);
    }

    public static void main(String[] args) {
        if (args.length < 3)
            StdOut.println("Wrong number of arguments");

        String expr1 = args[0];
        String op = args[1];
        String expr2 = args[2];

        Rational first = new Rational(expr1);
        Rational second = new Rational(expr2);

        Rational result = null;
        switch (op) {
            case "+":
                result = first.plus(second);
                break;
            case "-":
                result = first.minus(second);
                break;
            case "x":
                result = first.times(second);
                break;
            case "/":
                result = first.dividedBy(second);
                break;
            default:
                result = first;
                break;
        }

        StdOut.printf("%s %s %s = %s\n", expr1, op, expr2, result);
    }
}
