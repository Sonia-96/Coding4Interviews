import java.util.Arrays;

public class InPlaceHeapSort {
    int[] heap;
    int size;

    public InPlaceHeapSort(int[] nums) {
        heap = nums;
        size = nums.length;
    }

    public void heapSort() {
        int n = heap.length;
        // Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            sink(i);
        }
        // Sort the array
        for (int i = 1; i < n; i++) {
            removeBiggest();
        }
    }

    private void removeBiggest() {
        swap(0, --size);
        sink(0);
    }

    private void sink(int k) {
        int left = k * 2 + 1, right = k * 2 + 2, maxIndex = k;
        if (left < size && heap[left] > heap[k]) {
            maxIndex = left;
        }
        if (right < size && heap[right] > heap[maxIndex]) {
            maxIndex = right;
        }
        if (k != maxIndex) {
            swap(k, maxIndex);
            sink(maxIndex);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        InPlaceHeapSort hs = new InPlaceHeapSort(nums);
        hs.heapSort();
        System.out.print(Arrays.toString(nums));
    }
}
