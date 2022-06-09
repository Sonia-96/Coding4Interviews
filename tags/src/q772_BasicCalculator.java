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
                currNum = currNum * 10 + c - '0';
            } else if (c == '(') {
                int cnt = 1, j = i;
                while (cnt != 0) {
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
            if (!Character.isDigit(c) && c != ' ' && c != ')' || i == s.length() - 1) {
                if (operation == '+' || operation == '-') {
                    res += prevNum;
                    prevNum = operation == '+' ? currNum : -currNum;
                } else if (operation == '*') {
                    prevNum *= currNum;
                } else if (operation == '/') {
                    prevNum /= currNum;
                }
                operation = c;
                currNum = 0;
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
