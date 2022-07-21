import org.junit.Assert;
import org.junit.Test;

/**
 * Approach #2: Counting Array
 * - Time complexity: O(n)
 * - Space complexity: O(n)
 */
public class q1209b_RemoveAdjacentDuplicates {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        int[] counts = new int[sb.length()];
        for (int i = 0; i < sb.length(); i++) {
            if (i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
                counts[i] = 1;
            } else {
                counts[i] = counts[i - 1] + 1;
                if (counts[i] == k) {
                    sb.delete(i - k + 1, i + 1); // Strictly, this operation is in O(n - i) time
                    i = i - k; // this step is amazing!!
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
