import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class q392_isSubsequence {
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    @Test
    public void test() {
        assertFalse(isSubsequence("b", "c"));
        assertFalse(isSubsequence("aaaaaa", "bbaaaa"));
        assertFalse(isSubsequence("abc", ""));
    }
}
