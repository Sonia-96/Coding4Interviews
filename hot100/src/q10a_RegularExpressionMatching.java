import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q10a_RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        return match(s, 0, p, 0);
    }

    private boolean match(String s, int i, String p, int j) {
        if (j == p.length()) {
            return i == s.length();
        }
        boolean firstMatch = (i != s.length()) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            if (firstMatch) {
                return match(s, i + 1, p, j) || match(s, i, p, j + 2);
            }
            return match(s, i, p, j + 2);
        }
        return firstMatch && match(s, i + 1, p, j + 1);
    }

    @Test
    public void test() {
        assertFalse(isMatch("aa", "a"));
        assertTrue(isMatch("aa", "a*"));
        assertTrue(isMatch("bba", "b*bba"));
        assertTrue(isMatch("ab", ".*"));
        assertTrue(isMatch("aab", "c*a*b"));
        assertFalse(isMatch("mississipi", "mis*is*p*"));
    }
}
