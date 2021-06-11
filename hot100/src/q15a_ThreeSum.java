import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class q15a_ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        // 枚举第一个元素
        for (int i = 0; i < nums.length; i++) {
            // 避免第一个元素重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i], k = nums.length - 1;
            // 枚举第二个元素
            for (int j = i + 1; j < nums.length; j++) {
                // 避免第二个元素重复
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                while (j < k && nums[j] + nums[k] > target) {
                    k -= 1;
                }
                if (j == k) {
                    break;
                }
                if (nums[j] + nums[k] == target) {
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k])));
                }
            }
        }
        return res;
    }
}