import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q4_FindMedianSortedArrays {
    /**
     * Method 1: Merge Sort
     * Time complexity: Θ(m + n)
     * Memory complexity: Θ(m + n)
     */
    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int[] nums = mergeSort(nums1, nums2);
        if (nums.length % 2 == 1) {
            return nums[nums.length / 2];
        } else {
            return (nums[nums.length / 2] + nums[nums.length / 2 - 1] + 0.0) / 2;
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

    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            return getKthElement1(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            return (getKthElement1(nums1, nums2, midIndex1 + 1) + getKthElement1(nums1, nums2, midIndex2 + 1)) / 2;
        }
    }

    private double getKthElement1(int[] nums1, int[] nums2, int k) {
        int p1 = 0, p2 = 0;
        double curr = 0;
        for (int i = 0; i < k; i++) {
            if (p1 == nums1.length) {
                return nums2[p2 + k - i - 1];
            }
            if (p2 == nums2.length) {
                return nums1[p1 + k - i - 1];
            }
            if (nums1[p1] < nums2[p2]) {
                curr = nums1[p1];
                p1 += 1;
            } else {
                curr = nums2[p2];
                p2 += 1;
            }
        }
        return curr;
    }


    /**
     * Method 3: Binary Search
     * Time Complexity: Θ(log(m + n))
     * Memory Complexity: Θ(1)
     */
    public double findMedianSortedArrays3(int[] nums1, int[] nums2) {
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
    public void test1() {
        assertEquals(2, findMedianSortedArrays1(new int[] {1, 3}, new int[] {2}), 0.000001);
        assertEquals(2.5, findMedianSortedArrays1(new int[] {1, 2}, new int[] {3, 4}), 0.000001);
        assertEquals(1, findMedianSortedArrays1(new int[] {}, new int[] {1}), 0.000001);
        assertEquals(2, findMedianSortedArrays1(new int[] {2}, new int[] {}), 0.000001);
    }

    @Test
    public void test2() {
        int[] nums1 = {1, 3, 4, 9};
        int[] nums2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, findMedianSortedArrays2(nums1, nums2), 0.000001);
        assertEquals(2, findMedianSortedArrays2(new int[] {1, 3}, new int[] {2}), 0.000001);
        assertEquals(2.5, findMedianSortedArrays2(new int[] {1, 2}, new int[] {3, 4}), 0.000001);
        assertEquals(1, findMedianSortedArrays2(new int[] {}, new int[] {1}), 0.000001);
        assertEquals(2, findMedianSortedArrays2(new int[] {2}, new int[] {}), 0.000001);
    }

    @Test
    public void test3() {
        int[] nums1 = {1, 3, 4, 9};
        int[] nums2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, findMedianSortedArrays3(nums1, nums2), 0.000001);
        assertEquals(2, findMedianSortedArrays3(new int[] {1, 3}, new int[] {2}), 0.000001);
        assertEquals(2.5, findMedianSortedArrays3(new int[] {1, 2}, new int[] {3, 4}), 0.000001);
        assertEquals(1, findMedianSortedArrays3(new int[] {}, new int[] {1}), 0.000001);
        assertEquals(2, findMedianSortedArrays3(new int[] {2}, new int[] {}), 0.000001);
    }
}
