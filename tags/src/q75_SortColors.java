import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class q75_SortColors {
    // Counting sort (two pass)
    public void sortColors1(int[] nums) {
        int[] count = new int[3];
        Arrays.fill(count, 0);
        for (int e : nums) {
            count[e] += 1;
        }
        int pos = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                nums[pos++] = i;
                count[i] -= 1;
            }
        }
    }

    // Two pointers: indicate the index where we should put 0 and 1 (one pass)
    public void sortColors2(int[] nums) {
        int p0 = 0, p1 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, i, p0++);
                if (p0 < p1) {
                    swap(nums, i, p1++);
                }
                p1++;
            } else if (nums[i] == 1) {
                swap(nums, i, p1++);
            }
        }
    }

    /**
     * Two pointers - while loop:
     * indicate the index where we should put 0 and 2 (one pass)
     */
    public void sortColors3a(int[] nums) {
        int i= 0, p0 = 0, p2 = nums.length - 1;
        while (i <= p2) {
            if (nums[i] == 0 && i >= p0) {
                swap(nums, i, p0++);
            } else if (nums[i] == 2 && i <= p2) {
                swap(nums, i, p2--);
            } else {
                i++;
            }
        }
    }

    /**
     * Two pointers - for loop:
     * indicate the index where we should put 0 and 2 (one pass)
     */
    public void sortColors(int[] nums) {
        int p0 = 0, p2 = nums.length - 1;
        for (int i = 0; i <= p2; i++) {
            while (nums[i] == 2 && i <= p2) {
                swap(nums, i, p2--);
            }
            if (nums[i] == 0) {
                swap(nums, i, p0++);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int p = nums[i];
        nums[i] = nums[j];
        nums[j] = p;
    }

    @Test
    public void test1() {
        int[] nums = {2,0,2,1,1,0};
        sortColors(nums);
        Assert.assertArrayEquals(new int[] {0, 0, 1, 1, 2, 2}, nums);
    }

    @Test
    public void test2() {
        int[] nums = {2,0,1};
        sortColors(nums);
        Assert.assertArrayEquals(new int[] {0, 1, 2}, nums);
    }

    @Test
    public void test3() {
        int[] nums = {1, 2};
        sortColors(nums);
        Assert.assertArrayEquals(new int[] {1, 2}, nums);
    }

    @Test
    public void test4() {
        int[] nums = {0};
        sortColors(nums);
        Assert.assertArrayEquals(new int[] {0}, nums);
    }
}
