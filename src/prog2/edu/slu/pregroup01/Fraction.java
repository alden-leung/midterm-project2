package prog2.edu.slu.pregroup01;

/**
 * Represents a fraction with a numerator and denominator.
 *
 * Supports arithmetic operations (addition, subtraction, multiplication, division),
 * simplification, and conversion to string or double.
 */
public class Fraction {
    private int numerator;
    private int denominator;

    /**
     * Default constructor. Creates a fraction with value 0/1.
     */
    public Fraction() {
        this.numerator = 0;
        this.denominator = 1;
    }

    /**
     * Constructs a fraction representing a whole number.
     * @param wholeNumVal The integer value to be represented as a fraction (wholeNumVal/1)
     */
    public Fraction(int wholeNumVal) {
        this.numerator = wholeNumVal;
        this.denominator = 1;
    }

    /**
     * Constructs a fraction with a specified numerator and denominator.
     * @param numerator The numerator.
     * @param denominator The denominator (which cannot be zero).
     * @throws ArithmeticException If the denominator is zero.
     */
    public Fraction(int numerator, int denominator) throws ArithmeticException {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }

    /**
     * Sets the numerator of the fraction and simplifies the value.
     * @param num The new numerator.
     */
    public void setNumerator(int num) {
        this.numerator = num;
        simplify();
    }

    /**
     * Returns the numerator of the fraction.
     * @return The numerator.
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Returns the denominator of the fraction.
     * @return The denominator.
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Sets the denominator of the fraction and simplifies it.
     * @param den The new denominator (which cannot be zero).
     * @throws ArithmeticException If the denominator is zero
     */
    public void setDenominator(int den) throws ArithmeticException {
        if (den == 0) {
            throw new ArithmeticException("Denominator cannot be zero.");
        }
        this.denominator = den;
        simplify();
    }

    /**
     * Returns a string representation of the fraction.
     *
     * - If the denominator is 1,returns the numerator only.
     * - If the numerator is 0, returns "0".
     * - Otherwise, it returns "numerator/denominator".
     * @return The string representation.
     */
    public String toString() {
        if (denominator == 1) return numerator + "";
        if (numerator == 0) return "0";
        return numerator + "/" + denominator;
    }

    /**
     * Converts the fraction to a double value.
     * @return The decimal value of the fraction.
     */
    public double toDouble() {
        return (double) numerator / denominator;
    }

    /**
     * Adds another fraction to this fraction.
     * @param other The fraction to add.
     * @return A new Fraction representing the sum.
     */
    public Fraction add(Fraction other) {
        int num = this.numerator * other.denominator +
                other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    /**
     * Subtracts another fraction from this fraction.
     * @param other The fraction to subtract.
     * @return A new Fraction representing the difference.
     */
    public Fraction subtract(Fraction other) {
        int num = this.numerator * other.denominator -
                other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    /**
     * Multiplies this fraction by another fraction.
     * @param other The fraction to multiply by.
     * @return A new Fraction representing the product.
     */
    public Fraction multiplyBy(Fraction other) {
        int num = this.numerator * other.numerator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    /**
     * Divides this fraction by another fraction.
     * @param other The fraction to divide by.
     * @return A new Fraction representing the quotient
     * @throws ArithmeticException If attempting to divide by a fraction with numerator zero.
     */
    public Fraction divideBy(Fraction other) throws ArithmeticException {
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by zero fraction.");
        }
        int num = this.numerator * other.denominator;
        int den = this.denominator * other.numerator;
        return new Fraction(num, den);
    }

    /**
     * Simplifies the fraction to its lowest terms and ensures the denominator is positive.
     */
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

    /**
     * Calculates the greatest common divisor (GCD) using recursion.
     * @param a The first integer.
     * @param b The second integer.
     * @return The greatest common divisor.
     */
    private int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}