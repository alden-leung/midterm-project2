package prog2.edu.slu;

import prog2.edu.slu.pregroup01.Fraction;

/**
 * Represents a mixed number, which is a combination of a whole number and a fraction.
 * It extends the Fraction class, allowing operations with other MixedNumber or Fractioni objects.
 */
public class MixedNumber extends Fraction {

    /**
     * The whole part of the mixed number.
     */
    private int whole;

    /**
     * Default constructor.
     * Initializes the mixed number as 0 (0 whole part, 0 numerator, denominator 1).
     */
    public MixedNumber() {
        super();
        this.whole = 0;
    }

    /**
     * Constructs a MixedNumber from a whole number and a Fraction object.
     * @param whole The whole number part.
     * @param fraction The fractional part.
     */
    public MixedNumber(int whole, Fraction fraction) {
        super(fraction.getNumerator(), fraction.getDenominator());
        this.whole = whole;
    }

    /**
     * Constructs a MixedNumber from a whole number, numerator, and denominator.
     * @param whole The whole number part.
     * @param numerator The numerator of the fractional part.
     * @param denominator The denominator of the fractional part.
     */
    public MixedNumber(int whole, int numerator, int denominator){
        super(numerator, denominator);
        this.whole = whole;
    }

    /**
     * Constructs a MixedNumber from a Fraction object.
     * Automatically converts an improper fraction to a mixed number.
     * @param fraction The fraction object to convert.
     */
    public MixedNumber(Fraction fraction) {
        int num = fraction.getNumerator();
        int den = fraction.getDenominator();

        this.whole = num / den;
        setNumerator(num % den);
        setDenominator(den);
    }

    /**
     * Sets the whole part of the mixed number.
     * @param whole The whole number to set.
     */
    public void setWholePart(int whole) {
        this.whole = whole;
    }

    /**
     * Sets the fractional part of the mixed number.
     * @param fraction The Fraction object to set as the fractional part.
     */
    public void setFractionPart(Fraction fraction) {
        setNumerator(fraction.getNumerator());
        setDenominator(fraction.getDenominator());
    }

    /**
     * Returns the whole part of the mixed number.
     * @return The whole number part.
     */
    public int getWhole() {
        return whole;
    }

    /**
     * Returns the fractional part of the mixed number as a Fraction object.
     * @return The fractional part.
     */
    public Fraction getFractionPart() {
        return new Fraction(getNumerator(), getDenominator());
    }

    /**
     * Converts this mixed number to an improper fraction.
     * @return A Fraction object representing the mixed number as an improper fraction.
     */
    public Fraction toFraction() {
        int num = whole * getDenominator() + getNumerator();
        return new Fraction(num, getDenominator());
    }

    /**
     * Adds this mixed number to another mixed number.
     * @param other The other MixedNumber to add.
     * @return A new MixedNumber representing the sum of the two MixedNumbers.
     */
    public MixedNumber add(MixedNumber other) {
        Fraction result = this.toFraction().add(other.toFraction());
        return new MixedNumber(result);
    }

    /**
     * Subtracts another mixed number from this mixed number.
     * @param other The other MixedNumber to subtract
     * @return A new MixedNumber representing the difference of the two MixedNumbers.
     */
    public MixedNumber subtract(MixedNumber other) {
        Fraction result = this.toFraction().subtract(other.toFraction());
        return new MixedNumber(result);
    }

    /**
     * Multiplies this mixed number by another mixed number.
     * @param other The other MixedNumber to multiply by.
     * @return A new MixedNumber representing the product of the two MixedNumbers.
     */
    public MixedNumber multiplyBy(MixedNumber other) {
        Fraction result = this.toFraction().multiplyBy(other.toFraction());
        return new MixedNumber(result);
    }

    /**
     * Divides this mixed number by another mixed number.
     * @param other The other MixedNumber to divide by.
     * @return A new MixedNumber representing the quotient of the two MixedNumbers.
     */
    public MixedNumber divideBy(MixedNumber other) {
        Fraction result = this.toFraction().divideBy(other.toFraction());
        return new MixedNumber(result);
    }

    /**
     * Returns the string representation of the mixed number.
     * Formats as "whole numerator/denominator", but omits whole or fraction part if zero.
     * @return String representation of the mixed number.
     */
    public String toString() {
        if (getNumerator() == 0) {
            return "" + whole;
        }
        if (whole == 0) {
            return super.toString();
        }
        return whole + " " + getNumerator() + "/" + getDenominator();
    }

    /**
     * Returns the decimal value of the mixed number.
     * @return Decimal value as a double.
     */
    public double toDouble() {
        return whole + super.toDouble();
    }
}
