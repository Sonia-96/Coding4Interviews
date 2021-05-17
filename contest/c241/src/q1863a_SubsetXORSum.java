import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1863a_SubsetXORSum {
    public int total = 0;

    public int subsetXORSum(int[] nums) {
        backtrack(0, nums, 0);
//        dfs(0, nums, 0);
        return total;
    }


    // method 1: backtrack
    public void backtrack(int i, int[] nums, int curr) {
        total += curr;
        for (int j = i; j < nums.length; j++) {
            backtrack(j + 1, nums, curr ^ nums[j]);
        }
    }

    // method 2: dfs
    public void dfs(int i, int[] nums, int curr) {
        if (i == nums.length) {
            total += curr;
            return;
        }
        dfs(i + 1, nums, curr ^ nums[i]);
        dfs(i + 1, nums, curr);
    }

    @Test
    public void test() {
        assertEquals(6, subsetXORSum(new int[] {1, 3}));
        assertEquals(28, subsetXORSum(new int[] {5, 1, 6}));
        assertEquals(480, subsetXORSum(new int[] {3,4,5,6,7,8}));
    }
}
