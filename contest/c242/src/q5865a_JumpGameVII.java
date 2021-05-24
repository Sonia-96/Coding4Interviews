import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q5865a_JumpGameVII {
    // 动态规划 + 前缀数组
    public boolean canReach(String s, int minJump, int maxJump) {
        boolean[] dp = new boolean[s.length()];
        dp[0] = true;
        int[] pre = new int[s.length()];
        // 预处理minJump之前的前缀和
        for (int i = 0; i < minJump; i++) {
            pre[i] = 1;
        }
        // 从minJump开始动态规划
        for (int i = minJump; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                int left = i - maxJump, right = i - minJump;
                int total = pre[right] - (left > 0 ? pre[left - 1] : 0);
                dp[i] = total > 0;
            }
            pre[i] = pre[i - 1] + (dp[i] ? 1 : 0);
        }
        return dp[s.length() - 1];
    }

    @Test
    public void test() {
        assertTrue(canReach("011010", 2, 3));
        assertFalse(canReach("01101110", 2, 3));
        assertFalse(canReach("01", 1, 1));
        assertTrue(canReach("0000000000", 1, 5));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append('0');
        }
        String s = sb.toString();
        assertTrue(canReach(s, 1, 100000));
    }
}
