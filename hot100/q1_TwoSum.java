import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class q1_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        // key: num, value: index
        Map<Integer, Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[] {hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return null;
    }

    @Test
    public void test() {
        int[] nums1 = {2,11,15, 7};
        assertArrayEquals(new int[] {0, 3}, twoSum(nums1, 9));

        int[] nums2 = {3,2,4};
        assertArrayEquals(new int[] {1, 2}, twoSum(nums2, 6));

        int[] nums3 = {3, 3};
        assertArrayEquals(new int[] {0, 1}, twoSum(nums3, 6));
    }
}
