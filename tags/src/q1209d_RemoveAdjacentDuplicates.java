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
        Stack<Integer> counts = new Stack<>();
        char[] sa = s.toCharArray();
        int j = 0; // 慢指针
        for (int i = 0; i < sa.length; i++, j++) {
            sa[j] = sa[i];
            if (j == 0 || sa[j] != sa[j - 1]) {
                counts.push(1);
            } else {
                int cnt = counts.pop() + 1;
                if (cnt == k) {
                    j = j - k;
                } else {
                    counts.push(cnt);
                }
            }
        }
        return new String(sa, 0, j);
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
