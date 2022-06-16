import org.junit.Assert;
import org.junit.Test;

public class q227b_BasicCalculator {
    public int calculate(String s) {
        char operation = '+';
        int prevNum = 0, currNum = 0, res = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    i++;
                    currNum = currNum * 10 + s.charAt(i) - '0';
                }
            }
            if (i == s.length() - 1 || s.charAt(i) != ' ' && !Character.isDigit(s.charAt(i))) {
                if (operation == '+' || operation == '-') {
                    res += prevNum;
                    prevNum = operation == '+' ? currNum : -currNum;
                } else if (operation =='*') {
                    prevNum *= currNum;
                } else if (operation == '/') {
                    prevNum /= currNum;
                }
                operation = s.charAt(i);
            }
            i++;
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
