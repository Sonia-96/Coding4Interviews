import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q5_LongestPalindrome {
    public String longestPalindrome(String s) {
        if (s.length() < 2) {
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) { //所有长度为1的子串都是回文子串
            dp[i][i] = true;
        }
        int maxLen = 1, start = 0;
        for (int j = 1; j < s.length(); j++) { // j + 1为子串长度
            for (int i = 0; i + j < s.length(); i++) { // i为左边界，i + j为右边界
                if (s.charAt(i) != s.charAt(i + j)) {
                    dp[i][i + j] = false;
                } else {
                    if (j == 1) {
                        dp[i][i + j] = true;
                    } else {
                        dp[i][i + j] = dp[i + 1][i + j - 1];
                    }
                }
                if (dp[i][i + j] && j + 1 > maxLen) {
                    maxLen = j + 1;
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLen);
    }

    public String longestPalindrome2(String s) {
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
