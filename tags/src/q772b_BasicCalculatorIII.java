import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class q772b_BasicCalculatorIII {
    Map<Character, Integer> precedence;

    public q772b_BasicCalculatorIII() {
        precedence = new HashMap<>();
        precedence.put('(', -1); // 例，1 * (3 -，这种情况不能运算
        precedence.put('+', 0);
        precedence.put('-', 0);
        precedence.put('*', 1);
        precedence.put('/', 1);
    }

    public int calculate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    i++;
                    num = num * 10 + s.charAt(i) - '0';
                }
                nums.push(num);
            } else if (c == '(') {
                ops.push('(');
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    nums.push(operate(nums, ops));
                }
                ops.pop();
            } else if (c != ' ') {
                // 若当前运算符的优先级<=上一个运算符，如 1 * 2 +，则上一个运算符可以进行运算
                // 这里必须用while，不能用if
                // 因为operate是从后往前算的，如果不把前面能算的先算了，最后可能导致运算顺序改变
                // 例如，对于1*2-3*4+5*6, 使用if相当于计算1*2-(3*4+5*6)
                while (!ops.isEmpty() && compare(c, ops.peek()) <= 0) {
                    nums.push(operate(nums, ops));
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) {
            nums.push(operate(nums, ops));
        }
        return nums.pop();
    }

    private int operate(Stack<Integer> nums, Stack<Character> ops) {
        int a = nums.pop();
        int b = nums.isEmpty() ? 0 : nums.pop(); // 处理 -1 这种case
        char op = ops.pop();
        return switch (op) {
            case '+' -> b + a;
            case '-' -> b - a;
            case '*' -> b * a;
            case '/' -> b / a;
            default -> 0;
        };
    }

    private int compare(char op1, char op2) {
        return precedence.get(op1) - precedence.get(op2);
    }

    @Test
    public void test() {
        Assert.assertEquals(1, calculate(" 3/2 "));
        Assert.assertEquals(42, calculate("42"));
        Assert.assertEquals(20, calculate("1*2-3*4+5*6"));
        Assert.assertEquals(-24, calculate("1*2+3/4+5*6-7*8+9/10"));
        Assert.assertEquals(-24, calculate("1*2-3/4+5*6-7*8+9/10"));
        Assert.assertEquals(3, calculate("1+1*1+1*1"));
        Assert.assertEquals(-14, calculate("1+2*(2 - 3 * 4)+ 5"));
        Assert.assertEquals(0, calculate("1+2*(2 * 3 - 4 + 1)- (5 + 2)"));
        Assert.assertEquals(4, calculate("1+2*(3 - 4)+ 5"));
        Assert.assertEquals(-2147483648, calculate("0-2147483648"));
    }
}