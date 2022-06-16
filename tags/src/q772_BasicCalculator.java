import org.junit.Assert;
import org.junit.Test;

public class q772_BasicCalculator {
    public int calculate(String s) {
        int res = 0, prevNum = 0, currNum = 0;
        char operation = '+';
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    i++;
                    c = s.charAt(i);
                    currNum = currNum * 10 + c - '0';
                }
            } else if (c == '(') {
                int j = i, cnt = 1;
                while (cnt > 0) {
                    i++;
                    if (s.charAt(i) == '(') {
                        cnt++;
                    } else if (s.charAt(i) == ')') {
                        cnt--;
                    }
                }
                currNum = calculate(s.substring(j + 1, i));
                c = s.charAt(i);
            }
            if (i == s.length() - 1 || c != ' ' && !Character.isDigit(c)) {
                if (operation == '+' || operation == '-') {
                    res += prevNum;
                    prevNum = operation == '+' ? currNum : -currNum;
                } else if (operation == '*') {
                    prevNum *= currNum;
                } else if (operation == '/') {
                    prevNum /= currNum;
                }
                operation = c;
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
