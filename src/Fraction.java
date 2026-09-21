import java.util.Objects;

public class Fraction {
    private long numerator;
    private long denominator;

    public Fraction(long num, long den) {
        if (den == 0) throw new ArithmeticException("分母不能为0");
        long gcd = gcd(Math.abs(num), Math.abs(den));
        numerator = num / gcd;
        denominator = den / gcd;
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
    }

    // Getter方法，用来在外部读取私有分子分母
    public long getNumerator() {
        return numerator;
    }
    public long getDenominator() {
        return denominator;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public Fraction add(Fraction other) {
        long newNum = this.numerator * other.denominator + other.numerator * this.denominator;
        long newDen = this.denominator * other.denominator;
        return new Fraction(newNum, newDen);
    }

    public Fraction sub(Fraction other) {
        long newNum = this.numerator * other.denominator - other.numerator * this.denominator;
        long newDen = this.denominator * other.denominator;
        return new Fraction(newNum, newDen);
    }

    public Fraction mul(Fraction other) {
        long newNum = this.numerator * other.numerator;
        long newDen = this.denominator * other.denominator;
        return new Fraction(newNum, newDen);
    }

    public Fraction div(Fraction other) {
        return mul(new Fraction(other.denominator, other.numerator));
    }

    public boolean ge(Fraction o) {
        return this.numerator * o.denominator >= o.numerator * this.denominator;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        if (Math.abs(numerator) >= denominator) {
            long integer = numerator / denominator;
            long rem = numerator % denominator;
            return integer + "'" + Math.abs(rem) + "/" + denominator;
        } else {
            return numerator + "/" + denominator;
        }
    }

    public static Fraction parse(String s) {
        if (s.contains("'")) {
            String[] part = s.split("'");
            long intPart = Long.parseLong(part[0]);
            String frac = part[1];
            String[] nd = frac.split("/");
            long n = Long.parseLong(nd[0]);
            long d = Long.parseLong(nd[1]);
            return new Fraction(intPart * d + n, d);
        } else if (s.contains("/")) {
            String[] nd = s.split("/");
            long n = Long.parseLong(nd[0]);
            long d = Long.parseLong(nd[1]);
            return new Fraction(n, d);
        } else {
            return new Fraction(Long.parseLong(s), 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}


