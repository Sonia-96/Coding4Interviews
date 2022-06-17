import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class q224_BasicCalculatorI {
    public int calculate(String s) {
        int res = 0;
        int sign = 1;
        Stack<Integer> stack = new Stack<>();
        stack.push(sign);
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    i++;
                    num = num * 10 + s.charAt(i) - '0';
                }
                res += sign * num;
            } else {
                if (c == '+') {
                    sign = stack.peek();
                } else if (c == '-') {
                    sign = -stack.peek();
                } else if (c == '(') {
                    stack.push(sign);
                } else if (c == ')') {
                    stack.pop();
                }
            }
            i++;
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
