import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class q1209_RemoveAdjacentDuplicates {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> counts = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == s.charAt(i)) {
                counts.push(counts.pop() + 1);
            } else {
                counts.push(1);
            }
            sb.append(s.charAt(i));
            if (counts.peek() == k) {
                counts.pop();
                sb.delete(sb.length() - k, sb.length());
            }
        }
        return sb.toString();
    }

    @Test
    public void test1() {
        String s = "abcd";
        int k = 2;
        Assert.assertEquals("abcd", removeDuplicates(s, k));
    }

    @Test
    public void test2() {
        String s = "deeedbbcccbdaa";
        int k = 3;
        Assert.assertEquals("aa", removeDuplicates(s, k));
    }

    @Test
    public void test3() {
        String s = "pbbcggttciiippooaais";
        int k = 2;
        Assert.assertEquals("ps", removeDuplicates(s, k));
    }
}
