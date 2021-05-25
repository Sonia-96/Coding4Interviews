import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q5865b_JumpGameVII {
    // 动态规划 + 滑窗
    public boolean canReach(String s, int minJump, int maxJump) {
        boolean[] dp = new boolean[s.length()];
        dp[0] = true;
        int total = 1;
        for (int i = minJump; i < s.length(); i++) {
            int left = i - maxJump, right = i - minJump;
            if (right > 0) { // right > 0时窗口右端才开始移动
                total += dp[right] ? 1 : 0;
            }
            if (left > 0) { // left > 0时窗口左端才开始移动
                total -= dp[left - 1] ? 1 : 0;
            }
            dp[i] = s.charAt(i) == '0' && total >0;
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
