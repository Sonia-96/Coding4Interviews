import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class q224_BasicCalculatorI {
    public int calculate(String s) {
        int num = 0, res = 0;
        int sign = 1;
        Stack<Integer> stack = new Stack<>();
        stack.add(sign);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (i == s.length() - 1 || c != ' ' && !Character.isDigit(c)){
                res += sign * num;
                if (c == '+') {
                    sign = stack.peek();
                } else if (c == '-') {
                    sign = - stack.peek();
                } else if (c == '(') {
                    stack.push(sign);
                } else if (c == ')') {
                    stack.pop();
                }
                num = 0;
            }
        }
        return res;
    }

    @Test
    public void test1() {
        Assert.assertEquals(2, calculate("1 + 1"));
        Assert.assertEquals(3, calculate(" 2 - 1 + 2 "));
        Assert.assertEquals(23, calculate("(1+(4+5+2)-3)+(6+8)"));
        Assert.assertEquals(2147483647, calculate("2147483647"));
        Assert.assertEquals(-12, calculate("- (3 + (4 + 5))"));
        Assert.assertEquals(11, calculate("(7)-(0)+(4)"));
    }
}
