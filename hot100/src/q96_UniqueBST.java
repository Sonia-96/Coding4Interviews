import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q96_UniqueBST {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        // 序列长度为i
        for (int i = 2; i <= n; i++) {
            // 根节点从1变到i，其值记作j
            for (int j = 1; j <= i; j++) {
                // j-1为左子树的结点数，i-j为右子树的结点数
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    @Test
    public void test() {
        assertEquals(5, numTrees(3));
        assertEquals(1, numTrees(1));
        assertEquals(42, numTrees(5));
    }
}
