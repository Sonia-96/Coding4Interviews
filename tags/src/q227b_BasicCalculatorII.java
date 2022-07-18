import org.junit.Assert;
import org.junit.Test;

public class q227b_BasicCalculatorII {
    public int calculate(String s) {
        char op = '+';
        int prevNum = 0, currNum = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = currNum * 10 + c - '0';
            }
            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1){
                if (op == '+' || op == '-') {
                    res += prevNum;
                    prevNum = op == '+' ? currNum : -currNum;
                } else if (op == '*') {
                    prevNum *= currNum;
                } else if (op == '/') {
                    prevNum /= currNum;
                }
                op = c;
                currNum = 0;
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
