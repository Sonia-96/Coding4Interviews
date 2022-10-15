import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class q128a_LongestConsecutive {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);
        int longest = 0, count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                if(nums[i] == nums[i - 1] + 1) {
                    count++;
                } else {
                    longest = Math.max(longest, count);
                    count = 1;
                }
            }
        }
        return Math.max(longest, count);
    }

    @Test
    public void test() {
        Assertions.assertEquals(9, longestConsecutive(new int[] {0,3,7,2,5,8,4,6,0,1}));
        Assertions.assertEquals(3, longestConsecutive(new int[] {1,2,0,1}));
        Assertions.assertEquals(10, longestConsecutive(new int[] {0,1,2,4,8,5,6,7,9,3,55,88,77,99,999999999}));
    }
}
