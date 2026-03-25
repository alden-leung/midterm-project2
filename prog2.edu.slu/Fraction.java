public class Fraction {
    private int numerator;
    private int denominator;

    // Default constructor
    public Fraction() {
        this.numerator = 0;
        this.denominator = 1;
    }

    // Whole number
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

    // STRING CONSTRUCTOR
    public Fraction(String input) {
        input = input.trim();

        if (input.contains("/")) {
            String[] parts = input.split("/");
            this.numerator = Integer.parseInt(parts[0]);
            this.denominator = Integer.parseInt(parts[1]);
        } else {
            double value = Double.parseDouble(input);
            int scale = 10000;
            this.numerator = (int) Math.round(value * scale);
            this.denominator = scale;
        }

        if (denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        }

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

    public String toString() {
        if (numerator == 0) return "0";
        if (denominator == 1) return numerator + "";
        return numerator + "/" + denominator;
    }

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

    // Simplify
    private void simplify() {
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        numerator /= gcd;
        denominator /= gcd;

        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
    }

    private int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}