import java.util.HashMap;
import java.util.Stack;

public class q20_ValidParenthesis {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(s.charAt(i));
            } else {
                if (stack.empty()) {
                    return false;
                }
                char pre = stack.pop();
                if ((c == ')' && pre != '(') || (c == ']' && pre != '[') || (c == '}' && pre != '{')  ) {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    public boolean isValid2(String s) {
        if ((s.length() & 1) == 1) {
            return false;
        }
        HashMap<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put('}', '{');
            put(']', '[');
        }};
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (pairs.containsKey(s.charAt(i))) {
                if (stack.isEmpty() || pairs.get(s.charAt(i)) != stack.pop()) {
                    return false;
                }
            } else {
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }
}
