import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class q150_evalRPN {
    Set<String> operators = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

    public int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        for (String s : tokens) {
            stack.push(s);
        }
        return compute(stack);
    }

    private int compute(Stack<String> stack) {
        String op = stack.pop();
        if (operators.contains(op)) {
            int right = compute(stack);
            int left = compute(stack);
            switch (op) {
                case "+":
                    return left + right;
                case "-":
                    return left - right;
                case "*":
                    return left * right;
                case "/":
                    return left / right;
            }
        }
        return Integer.parseInt(op);
    }

    @Test
    public void test() {
        Assert.assertEquals(9, evalRPN(new String[] {"2","1","+","3","*"}));
        Assert.assertEquals(6, evalRPN(new String[] {"4","13","5","/","+"}));
        Assert.assertEquals(22, evalRPN(new String[] {"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
    }
}
