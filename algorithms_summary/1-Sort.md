# Merge Sort

The algorithm Merge Sort are divided into 2 phases:
-  Divide phase: Recursively split the array into two halves until there is only one element in the array.
-  Merge phase: Recursively sort each sub-array and merge them into a single sorted array.



Complexity analysis:

- Time complexity:
  - best: Θ(N)
  - worst: Θ(N)
  - average: Θ(N)
- Memory complexity: Θ(N)



## Array

```java
public class MergeSort {

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
}
```

## Linked List

```java
public class SortList {
    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = getMid(head);
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        return merge(left, right);
    }

    public ListNode merge(ListNode n1, ListNode n2) {
        ListNode sentinel = new ListNode(-1, null);
        ListNode p = sentinel;
        while (n1 != null && n2 != null) {
            if (n1.val < n2.val) {
                p.next = n1;
                n1 = n1.next;
            } else {
                p.next = n2;
                n2 = n2.next;
            }
            p = p.next;
        }
        p.next = n1 == null? n2 : n1;
        return sentinel.next;
    }

    public ListNode getMid(ListNode head) {
        ListNode midPrev = null;
        while (head != null && head.next != null) {
            midPrev = midPrev == null ? head : midPrev.next;
            head = head.next.next;
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        return mid;
    }
}

```

# Quick Sort

1. Choose a random number as a pivot
2. Iterate the array, put the numbers smaller than the pivot on its left, and numbers bigger than the pivot on its right
3. Recursively repeat the step 1 and 2 on the both sides of the partition

```java
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
}
```

# Heap Sort

## Heap

Heap is an implementation of Priority Queue.

- Max Heap: the parent's value is bigger than its children's values.
- Min Heap: the parent's value is smaller than its children's values.



**Heap operations**: (take min heap as an example)

- index:
  - 0-based index:
    - parent: i / 2
    - left children: i * 2 + 1
    - right children: i * 2 + 2
  - 1-based index
    - parent: (i - 1) / 2
    - left children: i * 2
    - right children: i * 2 + 1
- helper functions:
  - `swim(i)`: if the node is smaller than its parent, we swap them and continue to swim the new parent, until the heap satisfies the property of a min heap.
  - `sink(i)`: if the node is bigger than any of its children, we swap the node with the smallest children and continue to swim the new children, until the heap satisfies the property of a min heap.
- `add(x)`: add a node `x` to the end of the array, then swim the new node.
- `getSmallest()`: return the smallest value in the heap, which is the first element in the array.
- `removeSmallest()`: swap the first and last element in the array, reduce the size of the array by 1, and sink(0)
- `changePriority(i, p)`: change the i-th element's priority to p. if p is bigger than its old priority, then sink(i); otherwise swim(i).

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> heap; // 1-based index
    private HashMap<T, Integer> itemMapIndex; // key is item, value is index

    private class Node {
        T item;
        double priority;

        public Node(T i, double p) {
            item = i;
            priority = p;
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(null);
        itemMapIndex = new HashMap<>();
    }

    private void swim(int k) {
        int parent = parent(k);
        if (parent > 0 && heap.get(k).priority < heap.get(parent).priority) {
            swap(k, parent);
            swim(parent);
        }
    }

    private void sink(int k) {
        int left = left(k), right = right(k), minIndex = k;
        if (left <= size() && heap.get(minIndex).priority > heap.get(left).priority) {
            minIndex = left;
        }
        if (right <= size() && heap.get(minIndex).priority > heap.get(right).priority) {
            minIndex = right;
        }
        if (minIndex != k) {
            swap(k, minIndex);
            sink(minIndex);
        }
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        heap.add(new Node(item, priority));
        itemMapIndex.put(item, size() + 1);
        swim(size());

    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemMapIndex.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return heap.get(1).item;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T toReturn = heap.get(1).item;
        swap(1, size());
        sink(1);
        itemMapIndex.remove(toReturn);
        return toReturn;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return itemMapIndex.size();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = itemMapIndex.get(item);
        double oldPriority = heap.get(index).priority;
        heap.get(index).priority = priority;
        if (priority > oldPriority) {
            sink(index);
        } else {
            swim(index);
        }
    }

    private int parent(int k) {
        return k / 2;
    }

    private int left(int k) {
        return k * 2;
    }

    private int right(int k) {
        return k * 2 + 1;
    }

    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        itemMapIndex.replace(heap.get(i).item, i);
        itemMapIndex.replace(heap.get(j).item, j);
    }
}

```

## In-place heap sort

1. Build a heap: sink each element from the first non-leaf node to the root.
2. removeBiggest() for (n - 1) times

```java
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
}
```

Complexity analysis:

- Time complexity: Θ(NlogN)

  - Build the heap: amortized time complexity is Θ(N)

  - Sort the heap: Θ(NlogN)

- Space complexity: Θ(logN), used for space stack in recursive calls

​    
