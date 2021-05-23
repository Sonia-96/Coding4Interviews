import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q5865b_JumpGameVII {
    // 双指针
    public boolean canReach(String s, int minJump, int maxJump) {
        boolean[] dp = new boolean[s.length()];
        dp[0] = true;
        int p = 0, q = 1, preSum = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                int left = Math.max(0, i - maxJump);
                int right = i - minJump;
                if (left <= right) {
                    for(;q <= right; q++) {
                        preSum += dp[q] ? 1 : 0;
                    }
                    for (;p < left; p++) {
                        preSum -= dp[p] ? 1 : 0;
                    }
                    if (preSum > 0) {
                        dp[i] = true;
                    }
                }
            }
        }
        return dp[s.length() - 1];
    }

    @Test
    public void test() {
        assertTrue(canReach("011010", 2, 3));
        assertFalse(canReach("01101110", 2, 3));
        assertFalse(canReach("01", 1, 1));
        assertTrue(canReach("0000000000", 1, 5));
        assertFalse(canReach("00111010", 3, 5));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append('0');
        }
        String s = sb.toString();
        assertTrue(canReach(s, 1, 100000));
    }
}
