import org.junit.Assert;
import org.junit.Test;

public class q227b_BasicCalculator {
    public int calculate(String s) {
        int res = 0, currNum = 0, prevNum = 0;
        char operation = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = currNum * 10 + c - '0';
            }
            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1) {
                if (operation == '+' || operation == '-') {
                    res += prevNum;
                    prevNum = operation == '+' ? currNum : -currNum;
                } else if (operation =='*') {
                    prevNum *= currNum;
                } else {
                    prevNum /= currNum;
                }
                currNum = 0;
                operation = c;
            }
        }
        res += prevNum;
        return res;
    }

    @Test
    public void test() {
        Assert.assertEquals(7, calculate("3+2*2"));
        Assert.assertEquals(1, calculate(" 3/2 "));
        Assert.assertEquals(5, calculate(" 3+5 / 2 "));
        Assert.assertEquals(-12, calculate("0 - 12"));
    }
}
