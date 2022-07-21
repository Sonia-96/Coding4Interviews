import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class q1209_RemoveAdjacentDuplicates {
    public String removeDuplicates(String s, int k) {
        Stack<Integer> counts = new Stack<>();
        Stack<Character> elements = new Stack<>();
        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (elements.isEmpty() || elements.peek() != c) {
                counts.push(cnt);
                elements.add(c);
                cnt = 1;
            } else {
                if (++cnt == k) {
                    for (int j = 0; j < cnt - 1; j++) {
                        elements.pop();
                    }
                    cnt = counts.pop();
                } else {
                    elements.add(c);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : elements) {
            sb.append(c);
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
