import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class q3_LengthOfLongestSubstring {
    /**
     * Method1: Dynamic Programing
     * - Time Complexity: Θ(N)
     * - Memory Complexity: O(∣Σ∣)
     */
    public int lengthOfLongestSubstring(String s) {
        int[] prevIndex = new int[128];
        Arrays.fill(prevIndex, -1);
        int maxLen = 0, prevLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (prevIndex[s.charAt(i)] == -1 || i - prevIndex[s.charAt(i)] > prevLen) {
                prevLen += 1;
            } else {
                prevLen = i - prevIndex[s.charAt(i)];
            }
            prevIndex[s.charAt(i)] = i;
            if (prevLen > maxLen) {
                maxLen = prevLen;
            }
        }
        return maxLen;
    }

    /**
     * Method2: Sliding Window
     * - Time Complexity: Θ(N)
     * - Memory Complexity: O(∣Σ∣)
     */
    public int lengthOfLongestSubstring2(String s) {
        int rk = -1, res = 0;
        HashSet<Character> occ = new HashSet<>();
        for (int lk = 0; lk < s.length(); lk++) {
            while (rk + 1 < s.length() && !occ.contains(s.charAt(rk + 1))) {
                rk += 1;
                occ.add(s.charAt(rk));
            }
            res = Math.max(res, occ.size());
            occ.remove(s.charAt(lk));
        }
        return res;
    }

    @Test
    public void test1() {
        assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
        assertEquals(0, lengthOfLongestSubstring(""));
        assertEquals(1, lengthOfLongestSubstring("a"));
        assertEquals(1, lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    public void test2() {
        assertEquals(3, lengthOfLongestSubstring2("abcabcbb"));
        assertEquals(0, lengthOfLongestSubstring2(""));
        assertEquals(1, lengthOfLongestSubstring2("a"));
        assertEquals(1, lengthOfLongestSubstring2("bbbbb"));
        assertEquals(3, lengthOfLongestSubstring2("pwwkew"));
    }
}
