package prog2.edu.slu;

public class Fraction {
    private int numerator;
    private int denominator;

    // Default constructor
    public Fraction() {
        this.numerator = 0;
        this.denominator = 1;
    }

    // Whole number constructor
    public Fraction(int wholeNumVal) {
        this.numerator = wholeNumVal;
        this.denominator = 1;
    }

    // Main constructor
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }

    public void setNumerator(int num) {
        this.numerator = num;
        simplify();
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int den) {
        if (den == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        }
        this.denominator = den;
        simplify();
    }

    // Convert to string
    public String toString() {
        if (denominator == 1) return numerator + "";
        if (numerator == 0) return "0";
        return numerator + "/" + denominator;
    }

    // Convert to double
    public double toDouble() {
        return (double) numerator / denominator;
    }

    // ADD
    public Fraction add(Fraction other) {
        int num = this.numerator * other.denominator +
                other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    // SUBTRACT
    public Fraction subtract(Fraction other) {
        int num = this.numerator * other.denominator -
                other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    // MULTIPLY
    public Fraction multiplyBy(Fraction other) {
        int num = this.numerator * other.numerator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    // DIVIDE
    public Fraction divideBy(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by zero fraction.");
        }
        int num = this.numerator * other.denominator;
        int den = this.denominator * other.numerator;
        return new Fraction(num, den);
    }

    // Simplify fraction
    private void simplify() {
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        numerator /= gcd;
        denominator /= gcd;

        // denominator positive
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
    }

    // GCD
    private int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}