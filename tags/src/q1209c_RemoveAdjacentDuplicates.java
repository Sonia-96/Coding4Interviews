import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * Approach #3: Counting Stack
 * - Time complexity: O(n)
 * - Spce complexity: O(n)
 */
public class q1209c_RemoveAdjacentDuplicates {
    public String removeDuplicates(String s, int k) {
        Stack<Integer> counts = new Stack<>();
        StringBuilder sb = new StringBuilder(s);
        int cnt = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
                // 把最开始的0也放进去，还能避免pop时出现counts为空的情况
                counts.push(cnt);
                cnt = 1;
            } else if (++cnt == k) {
                sb.delete(i - k + 1, i + 1);
                i = i - k;
                cnt = counts.pop();
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
