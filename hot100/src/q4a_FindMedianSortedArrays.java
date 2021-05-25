import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q4a_FindMedianSortedArrays {
    /**
     * Method 1: Merge Sort
     * Time complexity: Θ(m + n)
     * Memory complexity: Θ(m + n)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums = mergeSort(nums1, nums2);
        if ((nums.length & 1) == 1) {
            return nums[nums.length / 2];
        } else {
            return (nums[nums.length / 2] + nums[nums.length / 2 - 1]) / 2.0;
        }
    }

    private int[] mergeSort(int[] nums1, int[] nums2) {
        int[] nums = new int[nums1.length + nums2.length];
        int p1 = 0, p2 = 0, p = 0;
        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] < nums2[p2]) {
                nums[p] = nums1[p1];
                p1 += 1;
            } else {
                nums[p] = nums2[p2];
                p2 += 1;
            }
            p += 1;
        }
        if (p1 < nums1.length) {
            System.arraycopy(nums1, p1, nums, p, nums1.length - p1);
        } else if (p2 < nums2.length) {
            System.arraycopy(nums2, p2, nums, p, nums2.length - p2);
        }
        return nums;
    }

    @Test
    public void test1() {
        assertEquals(2, findMedianSortedArrays(new int[] {1, 3}, new int[] {2}), 0.000001);
        assertEquals(2.5, findMedianSortedArrays(new int[] {1, 2}, new int[] {3, 4}), 0.000001);
        assertEquals(1, findMedianSortedArrays(new int[] {}, new int[] {1}), 0.000001);
        assertEquals(2, findMedianSortedArrays(new int[] {2}, new int[] {}), 0.000001);
    }
}
