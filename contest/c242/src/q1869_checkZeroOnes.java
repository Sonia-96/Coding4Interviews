import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class q1869_checkZeroOnes {
    public boolean checkZeroOnes(String s) {
        int len1 = consecutiveLength(s, '1');
        int len0 = consecutiveLength(s, '0');
        return len1 > len0;
    }

    public int consecutiveLength(String s, char c) {
        int maxLen = 0, cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                cnt += 1;
            } else {
                maxLen = Math.max(maxLen, cnt);
                cnt = 0;
            }
        }
        maxLen = Math.max(maxLen, cnt); // 对字符串末尾的连续子串
        return maxLen;
    }

    @Test
    public void test() {
//        assertTrue(checkZeroOnes("1101"));
//        assertFalse(checkZeroOnes("111000"));
//        assertFalse(checkZeroOnes("110100010"));
//        assertFalse(checkZeroOnes("011000111"));
        assertFalse(checkZeroOnes("111000"));
    }
}
