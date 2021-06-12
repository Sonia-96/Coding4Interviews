import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 双指针法的另一种写法
public class q15b_ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        // 枚举第一个元素
        for (int i = 0; i < nums.length - 2; i++) {
            // 避免第一个元素重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 双指针法找出剩余两个元素
            int target = -nums[i], left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) { // 避免第二个元素重复
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {// 避免第三个元素重复
                        right--;
                    }
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println(threeSum(new int[] {-1,0,1,2,-1,-4}));
        System.out.println(threeSum(new int[] {0, 0, 0}));
        System.out.println(threeSum(new int[] {1, 1}));
        System.out.println(threeSum(new int[] {0, 1, 1}));
    }
}
