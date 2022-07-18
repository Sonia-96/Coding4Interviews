import org.junit.Assert;
import org.junit.Test;

public class q772a_BasicCalculatorIII {
    public int calculate(String s) {
        int prevNum = 0, currNum = 0, res = 0;
        char op = '+';
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    currNum = currNum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            } else if (c == '(') {
                int start = i, count = 1;
                while (count > 0) {
                    i++;
                    if (s.charAt(i) == ')') {
                        count--;
                    } else if (s.charAt(i) == '(') {
                        count++;
                    }
                }
                currNum = calculate(s.substring(start + 1, i));
            }
            if (i == s.length() - 1 || c != ' ' && !Character.isDigit(c)) {
                if (op == '+' || op == '-') {
                    res += prevNum; //prevNum是op前面的数字
                    prevNum = op == '+' ? currNum : -currNum;
                } else if (op == '*') {
                    prevNum *= currNum;
                } else if (op == '/'){
                    prevNum /= currNum;
                }
                op = c;
            }
            i++;
        }
        res += prevNum;
        return res;
    }

    @Test
    public void test() {
        Assert.assertEquals(15, calculate("5+5*2"));
        Assert.assertEquals(4, calculate(" 6-4 / 2 "));
        Assert.assertEquals(21, calculate("2*(5+5*2)/3+(6/2+8)" ));
    }
}
