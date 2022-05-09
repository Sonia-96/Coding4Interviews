import java.util.Arrays;

public class QuickSort {
    int[] nums;

    public QuickSort(int[] n) {
        nums = n;
    }

    public void quickSort() {
        quickSort(0, nums.length - 1);
    }

    public void quickSort(int start, int end) {
        if (start >= end) {
            return;
        }
        int p = partition(start, end);
        quickSort(start, p - 1);
        quickSort(p + 1, end);
    }

    private int partition(int start, int end) {
        int index = start + (int) (Math.random() * (end - start + 1));
        int pivot = nums[index];
        int j = start; // elements smaller than the pivot are stored in nums[:j - 1]
        swap(start, index);
        for (int i = start + 1; i <= end; i++) {
            if (nums[i] < pivot) {
                swap(i, ++j);
            }
        }
        swap(start, j);
        return j;
    }

    private void swap(int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        QuickSort hs = new QuickSort(nums);
        hs.quickSort();
        System.out.print(Arrays.toString(nums));
    }
}
