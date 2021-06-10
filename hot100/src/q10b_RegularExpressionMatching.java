import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q10b_RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(j - 1) == '*') {
                    if (matches(s, i - 1, p, j - 2)) {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 2];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                } else if (matches(s, i - 1, p, j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    private boolean matches(String s, int i, String p, int j) {
        if (i == -1) {
            return false;
        }
        return s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
    }

    @Test
    public void test() {
        assertFalse(isMatch("aa", "a"));
        assertTrue(isMatch("aa", "a*"));
        assertTrue(isMatch("bba", "b*bba"));
        assertTrue(isMatch("ab", ".*"));
        assertTrue(isMatch("aab", "c*a*b"));
        assertFalse(isMatch("mississipi", "mis*is*p*"));
        assertTrue(isMatch("", "a*"));
    }
}
