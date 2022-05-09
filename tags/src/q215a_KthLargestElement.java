import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;

public class q215a_KthLargestElement {

    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int e : nums) {
            maxHeap.add(e);
        }
        for (int i = 1; i < k; i++) {
            maxHeap.poll();
        }
        return maxHeap.poll();
    }

    public int findKthLargest(int[] nums, int k) {
        int size = nums.length;
        // Build a max heap
        for (int i = size / 2 - 1; i >= 0; i--) {
            sink(nums, i, size);
        }
        // Remove the largest element for k - 1 times
        for (int i = 1; i < k; i++) {
            swap(nums, 0, --size);
            sink(nums, 0, size);
        }
        // Return the largest number
        return nums[0];
    }

    private void sink(int[] nums, int k, int size) {
        int left = k * 2 + 1, right = k * 2 + 2, maxIndex = k;
        if (left < size && nums[left] > nums[k]) {
            maxIndex = left;
        }
        if (right < size && nums[right] > nums[maxIndex]) {
            maxIndex = right;
        }
        if (k != maxIndex) {
            swap(nums, k, maxIndex);
            sink(nums, maxIndex, size);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @Test
    public void test1() {
        Assert.assertEquals(4, findKthLargest(new int[] {3,2,3,1,2,4,5,5,6}, 4));
        Assert.assertEquals(1, findKthLargest(new int[] {2, 1}, 2));
    }
}
