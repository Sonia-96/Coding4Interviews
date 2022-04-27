import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class MergeSort {

    /**
     * The algorithm MergeSort are divided into 2 phases:
     * 1) Divide phase: Recursively split the array into two halves until there is only one element in the array.
     * 2) Merge phase: Recursively sort each sub-array and merge them into a single sorted array.
     */
    public int[] mergeSort(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return ;
        }
        int mid = (left + right) / 2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    public void merge(int[] nums, int left, int mid, int right) {
        int[] A = new int[mid - left + 1];
        int[] B = new int[right - mid];
        System.arraycopy(nums, left, A, 0, A.length);
        System.arraycopy(nums, mid + 1, B, 0, B.length);
        int p1 = 0, p2 = 0;
        while (p1 < A.length && p2 < B.length) {
            if (A[p1] < B[p2]) {
                nums[left++] = A[p1++];
            } else {
                nums[left++] = B[p2++];
            }
        }
        System.arraycopy(A, p1, nums, left, A.length - p1);
        System.arraycopy(B, p2, nums, left, B.length - p2);
    }

    @Test
    public void test() {
        int[] target = new int[] {1, 2, 3, 6};
        assertArrayEquals(target, mergeSort(new int[] {3, 1, 6, 2}));
    }
}
