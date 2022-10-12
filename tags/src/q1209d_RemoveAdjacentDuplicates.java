import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * Approach #4: Two Pointers
 * StringBuilder.delete()实际是时间复杂度是O(n)，本方法对此进行了优化
 * - Time complexity: O(n)
 * - Spce complexity: O(n)
 */
public class q1209d_RemoveAdjacentDuplicates {
    public String removeDuplicates(String s, int k) {
        Stack<Integer> stack = new Stack<>();
        char sa[] = s.toCharArray();
        int slow = 0;
        for (int i = 0; i < s.length(); i++, slow++) {
            sa[slow] = sa[i];
            if (slow > 0 && sa[slow] == sa[slow - 1]) {
                stack.push(stack.pop() + 1);
            } else {
                stack.push(1);
            }
            if (stack.peek() == k) {
                slow -= k;
                stack.pop();
            }
        }
        return new String(sa, 0, slow);
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
