import org.junit.Test;

import static org.junit.Assert.*;

public class q5772 {
    public boolean isSumEqual(String firstWord, String secondWord, String targetWord) {
        return wordSum(firstWord) + wordSum(secondWord) == wordSum(targetWord);
    }

    private int wordSum(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 10 + s.charAt(i) - 'a';
        }
        return res;
    }

    @Test
    public void test() {
        assertEquals(123, wordSum("abcd"));
        assertTrue(isSumEqual("acb", "cba", "cdb"));
        assertFalse(isSumEqual("aaa", "a", "aab"));
    }
}
