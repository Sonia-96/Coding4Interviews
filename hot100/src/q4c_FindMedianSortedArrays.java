import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q4c_FindMedianSortedArrays {
    /**
     * Method 3: Binary Search
     * Time Complexity: Θ(log(m + n))
     * Memory Complexity: Θ(1)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length;
        if (totalLength % 2 == 1) {
            return getKthElement(nums1, nums2, totalLength / 2 + 1);
        } else {
            return (getKthElement(nums1, nums2, totalLength / 2) + getKthElement(nums1, nums2, totalLength / 2 + 1)) / 2;
        }
    }

    private double getKthElement(int[] nums1, int[] nums2, int k) {
        int p1 = 0, p2 = 0;
        while (true) {
            if (p1 == nums1.length) {
                return nums2[p2 + k - 1];
            }
            if (p2 == nums2.length) {
                return nums1[p1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[p1], nums2[p2]);
            }
            int newP1 = p1 + Math.min(k / 2, nums1.length) - 1;
            int newP2 = p2 + Math.min(k / 2, nums2.length) - 1;
            if (nums1[newP1] <= nums2[newP2]) {
                k -= (newP1 - p1 + 1);
                p1 = newP1 + 1;
            } else {
                k -= (newP2 - p2 + 1);
                p2 = newP2 + 1;
            }
        }
    }

    @Test
    public void test3() {
        int[] nums1 = {1, 3, 4, 9};
        int[] nums2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, findMedianSortedArrays(nums1, nums2), 0.000001);
        assertEquals(2, findMedianSortedArrays(new int[] {1, 3}, new int[] {2}), 0.000001);
        assertEquals(2.5, findMedianSortedArrays(new int[] {1, 2}, new int[] {3, 4}), 0.000001);
        assertEquals(1, findMedianSortedArrays(new int[] {}, new int[] {1}), 0.000001);
        assertEquals(2, findMedianSortedArrays(new int[] {2}, new int[] {}), 0.000001);
    }
}
