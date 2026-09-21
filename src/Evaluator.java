import java.util.Stack;

public class Evaluator {
    public static Fraction evaluate(String exprStr) {
        String s = exprStr.replace("×", "*").replace("÷", "/");
        String[] tokens = s.split(" ");
        Stack<Fraction> numStack = new Stack<>();
        Stack<String> opStack = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[+\\-*/]")) {
                while (!opStack.isEmpty() && priority(opStack.peek()) >= priority(token)) {
                    Fraction b = numStack.pop();
                    Fraction a = numStack.pop();
                    numStack.push(calc(a, b, opStack.pop()));
                }
                opStack.push(token);
            } else {
                numStack.push(Fraction.parse(token));
            }
        }
        while (!opStack.isEmpty()) {
            Fraction b = numStack.pop();
            Fraction a = numStack.pop();
            numStack.push(calc(a, b, opStack.pop()));
        }
        return numStack.pop();
    }

    private static int priority(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private static Fraction calc(Fraction a, Fraction b, String op) {
        return switch (op) {
            case "+" -> a.add(b);
            case "-" -> a.sub(b);
            case "*" -> a.mul(b);
            case "/" -> a.div(b);
            default -> throw new RuntimeException("非法运算符");
        };
    }
}
