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
        int[] occ = new int[128]; // 哈希表长度视字符范围而定
        Arrays.fill(occ, -1);
        int maxLen = 0, curLen = 0;
        for (int i = 0; i < s.length(); i++) {
            int d = i - occ[s.charAt(i)];
            if (d > curLen) {
                curLen += 1;
            } else {
                maxLen = Math.max(maxLen, curLen);
                curLen = d;
            }
            occ[s.charAt(i)] = i;
        }
        return Math.max(maxLen, curLen); // 考虑以最后一个字符结尾的子串
    }

    /**
     * Method2: Sliding Window
     * - Time Complexity: Θ(N)
     * - Memory Complexity: O(∣Σ∣)
     */
    public int lengthOfLongestSubstring2(String s) {
        HashSet<Character> occ = new HashSet<>();
        int right = -1, maxLen = 0;
        for (int left = 0; left < s.length(); left++) {
            while (right + 1 < s.length() && !occ.contains(s.charAt(right + 1))) {
                right += 1;
                occ.add(s.charAt(right));
            }
            maxLen = Math.max(occ.size(), maxLen);
            occ.remove(s.charAt(left));
        }
        return maxLen;
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
