import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Stack;

public class q20_ValidParentheses {
    public boolean isValid(String s) {
        if (s.length() % 2 == 1) return false;
        HashMap<Character, Character> pairs = new HashMap<>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!pairs.containsKey(c)) {
                stack.push(c);
            } else if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    @Test
    public void test() {
        Assert.assertEquals(false, isValid("]"));
    }
}
