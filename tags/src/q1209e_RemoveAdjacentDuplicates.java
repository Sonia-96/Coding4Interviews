import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * Approach #5 : Rebuilt by Stack
 * - Time complexity: O(n)
 * - Spce complexity: O(n)
 */
public class q1209e_RemoveAdjacentDuplicates {
    class Pair {
        char ch;
        int count;

        Pair(char c, int i) {
            ch = c;
            count = i;
        }
    }

    public String removeDuplicates(String s, int k) {
        Stack<Pair> counts = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (!counts.isEmpty() && s.charAt(i) == counts.peek().ch) {
                counts.peek().count += 1;
            } else {
                counts.push(new Pair(s.charAt(i), 1));
            }
            if (counts.peek().count == k) {
                counts.pop();
            }
        }
        StringBuilder sb = new StringBuilder();
        // Stack is implemented by array, so we can use for-each to count from the bottom to the top
        for (Pair p : counts) {
            for (int i = 0; i <p.count; i++) {
                sb.append(p.ch);
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
