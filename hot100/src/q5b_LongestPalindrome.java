import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q5b_LongestPalindrome {
    public String longestPalindrome(String s) {
        int maxLen = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                start = i - (len - 1) / 2;
                maxLen = len;
            }
        }
        return s.substring(start, start + maxLen);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    @Test
    public void test() {
        assertEquals("bb", longestPalindrome("cbbd"));
        assertEquals("a", longestPalindrome("a"));
        assertEquals("", longestPalindrome(""));
        assertEquals("cc", longestPalindrome("ccd"));
//        assertEquals("aba", longestPalindrome("babad"));
        assertEquals("a", longestPalindrome("abda"));
        assertEquals("aca", longestPalindrome("aacabdkacaa"));
        assertEquals("ccc", longestPalindrome("ccc"));
    }
}
