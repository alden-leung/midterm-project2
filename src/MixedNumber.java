public class MixedNumber extends Fraction {

    private int whole;

    public MixedNumber() {
        super();
        this.whole = 0;
    }

    public MixedNumber(int whole, Fraction fraction) {
        super(fraction.getNumerator(), fraction.getDenominator());
        this.whole = whole;
    }

    public MixedNumber(int whole, int numerator, int denominator){
        super(numerator, denominator);
        this.whole = whole;
    }

    public MixedNumber(Fraction fraction) {
        int num = fraction.getNumerator();
        int den = fraction.getDenominator();

        this.whole = num / den;
        setNumerator(num % den);
        setDenominator(den);
    }

    public void setWholePart(int whole) {
        this.whole = whole;
    }

    public void setFractionPart(Fraction fraction) {
        setNumerator(fraction.getNumerator());
        setDenominator(fraction.getDenominator());
    }

    public int getWhole() {
        return whole;
    }

    public Fraction getFractionPart() {
        return new Fraction(getNumerator(), getDenominator());
    }

    public Fraction toFraction() {
        int num = whole * getDenominator() + getNumerator();
        return new Fraction(num, getDenominator());
    }

    public MixedNumber add(MixedNumber other) {
        Fraction result = this.toFraction().add(other.toFraction());
        return new MixedNumber(result);
    }

    public MixedNumber subtract(MixedNumber other) {
        Fraction result = this.toFraction().subtract(other.toFraction());
        return new MixedNumber(result);
    }

    public MixedNumber multiplyBy(MixedNumber other) {
        Fraction result = this.toFraction().multiplyBy(other.toFraction());
        return new MixedNumber(result);
    }

    public MixedNumber divideBy(MixedNumber other) {
        Fraction result = this.toFraction().divideBy(other.toFraction());
        return new MixedNumber(result);
    }

    public String toString() {
        if (getNumerator() == 0) {
            return "" + whole;
        }
        return whole + " " + getNumerator() + "/" + getDenominator();
    }

    public double toDouble() {
        return whole + super.toDouble();
    }
}