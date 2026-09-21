import java.util.*;

public class Expression {
    private static final Random rand = new Random();
    private final String exprStr;
    private final Fraction value;
    private final String signature;

    private Expression(String exprStr, Fraction val, String sig) {
        this.exprStr = exprStr;
        this.value = val;
        this.signature = sig;
    }

    public String getExprStr() { return exprStr; }
    public Fraction getValue() { return value; }
    public String getSignature() { return signature; }

    public static Expression generate(int opCount, int maxOp, int range) {
        if (opCount == 0) {
            if (rand.nextDouble() < 0.4) {
                long num = rand.nextLong(range);
                return new Expression(String.valueOf(num), new Fraction(num,1), String.valueOf(num));
            } else {
                long n = rand.nextLong(range);
                long d = rand.nextLong(range -1)+1;
                Fraction f = new Fraction(n, d);
                return new Expression(f.toString(), f, f.toString());
            }
        }
        Expression left = generate(opCount -1, maxOp, range);
        Expression right = generate(opCount -1, maxOp, range);
        char[] ops = {'+', '-', '×', '÷'};
        char op = ops[rand.nextInt(4)];

        Fraction res;
        String sigL = left.signature;
        String sigR = right.signature;
        String sig;

        switch (op) {
            case '+':
                res = left.value.add(right.value);
                if (sigL.compareTo(sigR) > 0) {
                    String tmp = sigL; sigL = sigR; sigR = tmp;
                }
                sig = "(" + sigL + "+" + sigR + ")";
                break;
            case '×':
                res = left.value.mul(right.value);
                if (sigL.compareTo(sigR) > 0) {
                    String tmp = sigL; sigL = sigR; sigR = tmp;
                }
                sig = "(" + sigL + "*" + sigR + ")";
                break;
            case '-':
                if (!left.value.ge(right.value)) {
                    return generate(opCount, maxOp, range);
                }
                res = left.value.sub(right.value);
                sig = "(" + sigL + "-" + sigR + ")";
                break;
            case '÷':
                try {
                    res = left.value.div(right.value);
                } catch (ArithmeticException e) {
                    return generate(opCount, maxOp, range);
                }
                long num = res.getNumerator();
                long den = res.getDenominator();
                if (Math.abs(num) >= den) {
                    return generate(opCount, maxOp, range);
                }
                sig = "(" + sigL + "/" + sigR + ")";
                break;
            default:
                throw new RuntimeException();
        }
        String show = left.exprStr + " " + op + " " + right.exprStr;
        return new Expression(show, res, sig);
    }

    public static Expression createOne(int range) {
        int opCnt = rand.nextInt(3)+1;
        return generate(opCnt,3,range);
    }
}

