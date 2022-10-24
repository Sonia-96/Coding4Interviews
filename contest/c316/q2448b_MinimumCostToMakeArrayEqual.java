import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class q2448b_MinimumCostToMakeArrayEqual {
    private long computeCost(int[] nums, int[] cost, long target) {
        long result = 0L;
        for (int i = 0; i < nums.length; i++) {
            result += Math.abs(target - nums[i]) * cost[i];
        }
        return result;
    }

    public long minCost(int[] nums, int[] cost) {
        int low = nums[0], high = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > high) {
                high = nums[i];
            }
            if (nums[i] < low) {
                low = nums[i];
            }
        }
        while (high > low) {
            int mid = (high + low) / 2;
            long cost1 = computeCost(nums, cost, mid);
            long cost2 = computeCost(nums, cost, mid + 1);
            if (cost1 < cost2) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return computeCost(nums, cost, low);
    }

    @Test
    public void test() {
        int[] nums = {735103,366367,132236,133334,808160,113001,49051,735598,686615,665317,999793,426087,587000,649989,509946,743518};
        int[] cost = {724182,447415,723725,902336,600863,287644,13836,665183,448859,917248,397790,898215,790754,320604,468575,825614};
        System.out.println(minCost(nums, cost));

        Assertions.assertEquals(1, minCost(new int[] {1, 2}, new int[] {1, 100}));
    }
}
