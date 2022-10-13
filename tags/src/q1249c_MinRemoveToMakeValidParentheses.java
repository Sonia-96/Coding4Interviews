import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class q1249c_MinRemoveToMakeValidParentheses {
    public String minRemoveToMakeValid(String s) {
        // 1. scan forward, delete invalid )
        int balance = 0, openCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                openCount++;
                balance++;
            } else if (c == ')') {
                if (balance == 0) continue;
                balance--;
            }
            sb.append(c);
        }
        // 2. remove the rightmost (
        StringBuilder res = new StringBuilder();
        int openKeep = openCount - balance;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(') {
                if (openKeep == 0) continue;
                openKeep--;
            }
            res.append(sb.charAt(i));
        }
        return res.toString();
    }

    @Test
    public void test() {
        Assertions.assertEquals("ab(c)d", minRemoveToMakeValid("a)b(c)d"));
        Assertions.assertEquals("", minRemoveToMakeValid("))(("));
    }
}
