import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1863b_SubsetXORSum {
    // 二进制模拟
    public int subsetXORSum(int[] nums) {
        int res = 0;
        int n = 1 << nums.length;
        for (int i = 0; i < n; i++) {
            int curr = 0;
            int index = i;
            for (int j = 0; j < nums.length; j++) {
                if ((index & 1) == 1) {
                    curr = curr ^ nums[j];
                }
                index = index >> 1;
            }
            res += curr;
        }
        return res;
    }

    @Test
    public void test() {
        assertEquals(6, subsetXORSum(new int[] {1, 3}));
        assertEquals(28, subsetXORSum(new int[] {5, 1, 6}));
        assertEquals(480, subsetXORSum(new int[] {3,4,5,6,7,8}));
    }
}
