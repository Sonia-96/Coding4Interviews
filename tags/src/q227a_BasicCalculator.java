import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class q227a_BasicCalculator {

    public int calculate(String s) {
        int num = 0;
        char operation = '+'; // the operation before the num
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (i == s.length() - 1 || !Character.isDigit(c) && c != ' ') {
                if (operation == '+') {
                    stack.push(num);
                } else if (operation == '-') {
                    stack.push(-num);
                } else if (operation == '*') {
                    stack.push(stack.pop() * num);
                } else if (operation == '/') {
                    stack.push(stack.pop() / num);
                }
                operation = c;
                num = 0;
            }
        }
        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }


    @Test
    public void test1() {
        Assert.assertEquals(7, calculate("3+2*2"));
        Assert.assertEquals(1, calculate(" 3/2 "));
        Assert.assertEquals(5, calculate(" 3+5 / 2 "));
        Assert.assertEquals(-12, calculate("0 - 12"));
    }
}
