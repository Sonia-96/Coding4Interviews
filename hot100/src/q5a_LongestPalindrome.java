import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q5a_LongestPalindrome {
    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) { // 所有长度为1的子串都是回文串
            dp[i][i] = true;
        }
        int maxLen = 1, start = 0;
        for (int len = 2; len <= s.length(); len++) { // len为子串长度
            for (int left = 0; left + len - 1 < s.length(); left++) { // left为左边界, (left + len - 1)为右边界
                int right = left + len - 1; // 右边界
                if (s.charAt(left) != s.charAt(right)) {
                    dp[left][right] = false;
                } else {
                    if (len == 2) {
                        dp[left][right] = true;
                    } else {
                        dp[left][right] = dp[left + 1][right - 1];
                    }
                }
                if (dp[left][right] && len > maxLen) {
                    maxLen = len;
                    start = left;
                }
            }
        }
        return s.substring(start, start + maxLen);
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
