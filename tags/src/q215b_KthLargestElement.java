import org.junit.Assert;
import org.junit.Test;

public class q215b_KthLargestElement {
    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, k, 0, nums.length - 1);
    }

    public int quickSelect(int[] nums, int k, int start, int end) {
        int p = randomPartition(nums, start, end);
        if (p == k - 1) {
            return nums[p];
        } else if (p < k - 1) {
            return quickSelect(nums, k, p + 1, end);
        } else {
            return quickSelect(nums, k, start, p - 1);
        }
    }

    private int randomPartition(int[] nums, int start, int end) {
        int index = start + (int) (Math.random() * (end - start + 1));
        int pivot = nums[index];
        int p = start;
        swap(nums, start, index);
        for (int i = start + 1; i <= end; i++) {
            if (nums[i] >= pivot) {
                swap(nums, i, ++p);
            }
        }
        swap(nums, start, p);
        return p;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @Test
    public void test1() {
        findKthLargest(new int[] {3,2,3,1,2,4,5,5,6}, 4);
        Assert.assertEquals(4, findKthLargest(new int[] {3,2,3,1,2,4,5,5,6}, 4));
        Assert.assertEquals(1, findKthLargest(new int[] {2, 1}, 2));
    }
}
