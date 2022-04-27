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
public class q148_SortList {
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

