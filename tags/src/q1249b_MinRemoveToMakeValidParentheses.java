import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class q1249b_MinRemoveToMakeValidParentheses {
    private StringBuilder removeInvalidClosing (CharSequence s, char open, char close) {
        int balance = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == open) {
                balance++;
            } else if (c == close) {
                if (balance == 0) continue;
                balance--;
            }
            sb.append(c);
        }
        return sb.reverse();
    }

    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = removeInvalidClosing(s, '(', ')');
        sb = removeInvalidClosing(sb, ')', '(');
        return sb.toString();
    }

    @Test
    public void test() {
        Assertions.assertEquals("ab(c)d", minRemoveToMakeValid("a)b(c)d"));
    }
}
