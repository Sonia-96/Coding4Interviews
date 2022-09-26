import org.junit.Assert;
import org.junit.Test;

/**
 * Approach #1: Brute Force （Time limit exceeded)
 * - Time complexity: O(n^2/k)
 * - Spce complexity: O(1)
 */
public class q1209a_RemoveAdjacentDuplicates {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        int length = -1;
        while (length != sb.length()) {
            length = sb.length();
            int count = 0;
            for (int i = 0; i < sb.length(); i++) {
                if (i == 0 || sb.charAt(i - 1) != sb.charAt(i)) {
                    count = 1;
                } else if (++count == k) {
                    sb.delete(i - k + 1, i + 1);
                }
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
