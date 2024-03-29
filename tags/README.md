[TOC]

reference: https://zhuanlan.zhihu.com/p/349940945

# 1 Sort

| No.                            | Difficult | Tags                         | Last Completed | High-F |
| ------------------------------ | --------- | ---------------------------- | -------------- | ------ |
| 148. Sort List                 | Medium    | Merge Sort, Linked List      | 2022-04-27     |        |
| 56. Merge Intervals            | Medium    | Sort                         | 2022-05-01     |        |
| 27. Remove Elements            | Easy      | Two pointers                 | 2022-05-01     |        |
| 179. Largest Number            | Medium    | String, Comparator           | 2022-05-01     |        |
| 75. Sort Colors                | Medium    | Two pointers                 | 2022-05-08     |        |
| **215. Kth Largest Element**   | Medium    | Randomized-select, Heap sort | 2022-05-09     | True   |
| 4. Median of Two Sorted Arrays | Hard      | Binary search                | 2022-05-09     | True   |
|                                |           |                              |                |        |
|                                |           |                              |                |        |

## 4. Median of Two Sorted Arrays

### Approach 1: Binary Search

- Time complexity: Θ(log(m + n))
- Space complexity: Θ(1)

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length + nums2.length;
        if ((n & 1) == 1) {
            return findKthElement(nums1, nums2, n / 2 + 1);
        } else {
            return (double) (findKthElement(nums1, nums2, n / 2 + 1) + findKthElement(nums1, nums2, n / 2)) / 2;
        }
    }
    
    private int findKthElement(int[] nums1, int[] nums2, int k) {
        int p1 = 0, p2 = 0;
        int newP1, newP2;
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
            newP1 = Math.min(p1 + k / 2 - 1, nums1.length - 1);
            newP2 = Math.min(p2 + k / 2 - 1, nums2.length - 1);
            if (nums1[newP1] < nums2[newP2]) {
                k -= newP1 - p1 + 1;
                p1 = newP1 + 1;
            } else {
                k -= newP2 - p2 + 1;
                p2 = newP2 + 1;
            }
        }
    }
}
```



## 27. Remove Element

### Approach 1: Two pointers-when elements to remove are many

Use fast pointer `j` to iterate the element in the array and slow pointer `i` to record the last position of the elements that  are not equal to the `val`. If `nums[j] ≠ val`, we copy `nums[j]` to `nums[i]` and increment both indexes. Otherwise, only increment `j`.

Complexity analysis:

- Time complexity: Θ(N)
- Space complexity: Θ(1)

```java
class Solution {
    public int removeElement2(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }
}
```

### Approach 2: Two pointers-when elements to remove are rare

Approach 1 has a disadvantage: if there are few elements equal to the `val`, this program will execute the step `nums[i++] = nums[j];` too many times. For example, nums=[1,2,3,5,4], val=4. In this case, it's better to directly move the elements equal to `val` to the end of the array and reduce the size by 1.

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }
        return n;
    }
}
```

## 56. Merge Intervals

### Approach 1: Sorting

1. Sort the intervals by their `start` value in ascending order
2. Iterate the sorted intervals. If the previous interval's `end` value is larger or equal than the current interval's `start` value, then they can be merged. The merged interval's `end`value is `Math.max(prevInterval[1], currInterval[1])`. Here we can either use for loop or while loop to implement it.

Complexity analysis:

- Time complexity: Θ(NlogN)
- Space complexity: O(N). O(N) space for the returned array, and O(logN) space for the sorting

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        LinkedList<int[]> merged = new LinkedList<>();
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.addLast(interval);
            } else {
                merged.getLast()[1] = Math.max(interval[1], merged.getLast()[1]);
            }
        }
        return merged.toArray(new int[merged.size()][2]);
    }

    // while loop
    public int[][] merge2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        ArrayList<int[]> res = new ArrayList<>();
        int i = 0;
        while (i < intervals.length) {
            int start = intervals[i][0], end = intervals[i][1];
            while (i < intervals.length && end >= intervals[i][0]) {
                end = Math.max(intervals[i][1], end);
                i++;
            }
            res.add(new int[] {start, end});
        }
        return res.toArray(new int[res.size()][]);
    }
}
```

## 75. Sort Colors

Requirement: **one-pass algorithm** with constant extra space. A one-pass algorithm reads the input exactly once. It generally requires O(N) time and less than O(N) space.

### Approach 1: Counting sort

Since there are only three types of digits in the array, we can use counting sort to solve this problem. Technically, this is a two-pass algorithm. In the first scan, we count the occurences of each number. In the second scan, we rearrange the whole array according to their occurences. 

Complexity analysis:

- Time complexity: Θ(2N)
- Space complexity: Θ(1)

```java
class Solution {
    public void sortColors(int[] nums) {
        int[] count = new int[3];
        Arrays.fill(count, 0);
        for (int e : nums) {
            count[e] += 1;
        }
        int pos = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                nums[pos++] = i;
                count[i] -= 1;
            }
        }
    }
}
```

### Approach 2: Two pointers

Use two pointers to indicate where we should put 0, 1, and 2. This is a one-pass algorithm.

#### 0, 1

Use pointer `p0` to indicate the index where we should put 0 and `p1` to indicate where to put 1. The initial value of them is 0. 

Use a pointer `i` to scan the array:

- if `nums[i] == 0`,  swap `nums[i]` and `nums[p0]`, then `p0` plus 1. 
  - Note, if `p0` is smaller than `p1`, then 1 will be swapped to the position `i`. At this time, we should swap nums[i] and nums[p1] and p1++.
- if `nums[i] == 1`,  swap `nums[i]` and `nums[p1]`, then `p1` plus 1.

```java
class SOlution {
    // Two pointers: indicate the index where we should put 0 and 1 (one pass)
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, i, p0++);
                if (p0 < p1) {
                    swap(nums, i, p1);
                }
                p1++;
            } else if (nums[i] == 1) {
                swap(nums, i, p1++);
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int p = nums[i];
        nums[i] = nums[j];
        nums[j] = p;
    }
}
```

#### 0, 2

In the previous method, we need to do unnecessary swap operation when `nums[i] == 0 && p0 < p1`. This is because the area of 0 is adjacent to the area of 1. When we exapand the area of 0, we must move the area of 1 to the right. This problem can be solved when we use two pointers to mark the areas of 0 and 2, which are not contiguous. 

To be specific, use the pointer `p0` to indicate the index where we should put 0 and `p2` to indicate where to put 2. The initial value of `p0` is 0, and `p2` is `nums.length - 1`.

Use a pointer `i` to scan the array from the position of `0` to the position of `p2`:

1. if `nums[i] == 0`,  swap `nums[i]` and `nums[p0]`, then `p0` plus 1. 
2. if `nums[i] == 2`,  swap `nums[i]` and `nums[p2]`, then `p1` minus 1.
  - After the swap, the new `nums[i]` may be 0 or 2. If `nums[i]` is 2, we should repeat the step 2 until `nums[i]` is not 2. If `nums[i]` is 0, repeat the step 1, and new `nums[i]` can only be 1.

```java
class Solution {
    public void sortColors(int[] nums) {
        int p0 = 0, p2 = nums.length - 1;
        for (int i = 0; i <= p2; i++) {
            // i <= p2 is to deal with the case when i == p2, e.g., [2]
            while (nums[i] == 2 && i <= p2) {
                swap(nums, i, p2--);
            }
            if (nums[i] == 0) {
                swap(nums, i, p0++);
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int p = nums[i];
        nums[i] = nums[j];
        nums[j] = p;
    }
}
```

We can also use while loop to implement this algorithm:

```java
	public void sortColors3a(int[] nums) {
        int i= 0, p0 = 0, p2 = nums.length - 1;
        while (i <= p2) {
            if (nums[i] == 0 && i >= p0) {
                swap(nums, i, p0++);
            } else if (nums[i] == 2 && i <= p2) {
                swap(nums, i, p2--);
            } else {
                i++;
            }
        }
    }
```

## 148. Sort List

### Approach 1: Merge sort

1. Find the middle node of the list (use fast and slow pointer) and split the list into `left` and `right` list
2. Sort each list and merge them into one single sorted list

- Time complexity: Θ(NlogN)
- Space complexity: Θ(1)

```java
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = getMid(head);
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        return merge(left, right);
    }
    
    private ListNode getMid(ListNode head) {
        ListNode midPrev = null;
        while (head != null && head.next != null) {
            midPrev = midPrev == null ? head : midPrev.next;
            head = head.next.next;
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        return mid;
    }
    
    private ListNode merge(ListNode left, ListNode right) {
        ListNode sentinel = new ListNode(-1, null);
        ListNode p = sentinel;
        while (left != null && right != null) {
            if (left.val < right.val) {
                p.next = left;
                left = left.next;
            } else {
                p.next = right;
                right = right.next;
            }
            p = p.next;
        }
        p.next = left == null ? right : left;
        return sentinel.next;
    }
}
```

## 179. Largest Number

### Approach 1: Sorting via custom comparator

1. Convert each integer into a string

2. Sort the array in descending order. The rule is: if s1 + s2 > s2 + s1, then s1 is bigger than s2. Once the array is sorted, the most "significant" number will be at the front.

3. There is a minor edge case that comes up when the array consists of only zeroes, so if the most sifnificant number is 0, we can simply return 0.

4. Iterate the array and concatenate each string. The result is the largest number.  

```java
class Solution {
    public String largestNumber(int[] nums) {
        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = Integer.toString(nums[i]);
        }
        Arrays.sort(strings, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));
        if (strings[0].equals("0")) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        for (String s : strings) {
            res.append(s);
        }
        return res.toString();
    }
}
```

## 215. Kth Largest Element in an Array

### Approach 1: In-place heap Sort

Build a max heap, do the `remove()` operation for `k - 1` times, then return the biggest element.

Although we can use the class `PriorityQueue` to solve this problem, it is always required to implement the heap by yourself in the interview. Therefore, it is recommended to build the heap from scratch. 

Complexity analysis:

- Time complexity: Θ(NlogN)
- Space complexity: Θ(N)

#### Use the class PriorityQueue

```java
class Solution {
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int e : nums) {
            maxHeap.add(e);
        }
        for (int i = 1; i < k; i++) {
            maxHeap.poll();
        }
        return maxHeap.poll();
    }
}
```

#### Build the heap by yourself

```java
class Solution {
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
}
```

### Approach 2: Randomized-select

The algorithm **randomized-select** is modeled after quick sort. As in quick sort, we partition the input array recursively. But unlike quick sort, which recursively processes both sides of the partition, randomized-select works on only onside of the partition. To be specific, the `partition()` function returns the index of the pivot, which is `p`. If `p` equals `k`, we return this value. if `p` is smaller than `k`, we only partition the right side recursively. Otherwise, we partition the right side recursively.

Complexity analysis:

- Time complexity: Θ(N) (see Introduction to Algorithms 9.2)
- Space complexity: Θ(logN)

```java
class Solution {
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
}
```

# 2 Linked List

| No.                                           | Difficult | Tags                               | First Completed | High-F |
| --------------------------------------------- | --------- | ---------------------------------- | --------------- | ------ |
| **206. Reverse Linked List**                  | Easy      | Linked list, Recursion             | 2022-05-09      |        |
| 876. Middle of the Linked List                | Easy      | Linked list, Two pointers          | 2022-04-27      |        |
| 160. Intersection of Two Linked Lists         | Easy      | Linked list, Two pointers          | 2022-05-11      |        |
| 141. Linked List Cycle (Linked List Cycle II) | Easy      | Linked list, Hashing, Two pointers | 2022-05-12      |        |
| **92. Reverse Linked List II**                | Medium    | Linked list                        | 2022-05-13      |        |
| **328. Odd Even Linked List**                 | Medium    | Linked list                        | 2022-05-13      |        |

## 92. Reverse Linked List II

Requirement: one-pass algorithm

### Approach 1

1. Find the node previous to the position `left`
2. Reverse the linked list from `left` to `right`
3. Link each part of the linked list

<img src="images/q92.png" alt="q92" style="zoom:20%;" />



```java
public ListNode reverseBetween(ListNode head, int left, int right) {
        // create a sentinel node
        ListNode sentinel = new ListNode(-1);
        sentinel.next = head;
        // find the node previous to the position left
        ListNode leftPrev = sentinel;
        for (int i = 0; i < left - 1; i++) {
            leftPrev = leftPrev.next;
        }
        // reverse the linked list from the position left to right
        ListNode prev = null, curr = leftPrev.next, next;
        for (int i = left; i <= right; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // link each part
        leftPrev.next.next = curr;
        leftPrev.next = prev;
        return sentinel.next;
    }
```

Complexity analysis:

- Time complexity: Θ(N) 
- Space complexity: Θ(1)

### Approach 2

In the area that needs to be reversed, we loop through the nodes and insert each node to the start position in this area.

<img src="images/1615105242-ZHlvOn-image.png" alt="image.png" style="zoom:50%;" />

The following is the explanation of how to insert the node:

Use three pointers:

- `pre`: always point to the node previous to the reversed area
- `curr`: always point to the first node in the reversed area. For example, in the following picture, `curr` always point to the node with the value 2.
- `next`: the node next to the `curr`

<img src="images/1615105296-bmiPxl-image.png" alt="image.png" style="zoom: 33%;" />

Change the `next` pointers of nodes `pre`, `curr`, and `next`:

① curr.next = next.next

② next.next = pre.next

③ pre.next = next

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode sentinel = new ListNode(-1);
        sentinel.next = head;
        // find the node previous to the position left
        ListNode leftPrev = sentinel;
        for (int i = 0; i < left - 1; i++) {
            leftPrev = leftPrev.next;
        }
        ListNode curr = leftPrev.next, next;
        for (int i = left; i < right; i++) {
            next = curr.next;
            curr.next = next.next;
            next.next = leftPrev.next;
            leftPrev.next = next;
        }
        return sentinel.next;
    }
}
```

Complexity analysis:

- Time complexity: Θ(N) 
- Space complexity: Θ(1)

## 141. Linked List Cycle

### Approach 1: Hashing

Traverse the linked list, and use a hash set to store visited nodes. While traversing, if the current node has been contained in the hash set, the linked list has a cycle. If the current node becomes `null`, the list has no cycle.

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> visited = new HashSet<>();
        while (head != null) {
            if (!visited.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }
}
```

Complexity analysis:

- Time complexity: Θ(N)
- Space complexity: Θ(N)

### Approach 2: Fast and slow pointers

Use 2 pointers, `slow` and `fast`. The `slow` moves step by step, and `fast` moves two steps at a time. If the linked list has a cycle, two pointers will meet at some point. Otherwise, the `fast`  or `fast.next` will become `null`.

```java
class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
```

Complexity analysis:

- Time complexity: Θ(N)
- Space complexity: Θ(1)

## 160. Intersection of Two Linked Lists

解析&证明：https://leetcode.cn/problems/intersection-of-two-linked-lists/solution/xiang-jiao-lian-biao-by-leetcode-solutio-a8jn/

```java
class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
         if (headA == null || headB == null) {
             return null;
         }
         ListNode pA = headA, pB = headB;
         while (pA != pB) {
             pA = pA == null ? headB : pA.next;
             pB = pB == null ? headA : pB.next;
         }
         return pA;
    }
}
```

## 206. Reverse Linked List

### Approach 1: Iteration

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head, next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
```

### Approach 2: Recursion

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
```

## 328. Odd Even Linked List

Requirement: You must solve the problem in `O(1)` extra space complexity and `O(n)` time complexity.

### Approach 1

Put the odd nodes in a linked list and even nodes in another. Than link the `evenList` to the tail of the `oddList`.

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
```

### Approach 2

Loop through each odd node, and put this node at the end of the `oddList` but in front of the `evenList`.

We use 4 pointers to mark import node:

- `oddLast`: the last node of the `oddList`
- `evenLast`: the last node of the `evenList`
- `curr`: current odd node
- `next`: the even node after `curr`

<img src="images/q328.jpg" alt="q328" style="zoom:33%;" />



```java
class Solution {
    public ListNode oddEvenList2(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode oddLast = head, evenLast = head.next;
        ListNode curr = evenLast.next, next;
        while (curr != null) {
            next = curr.next;
            curr.next = oddLast.next;
            evenLast.next = next;
            oddLast.next = curr;
            oddLast = curr;
            evenLast = next;
            curr = next == null ? null : next.next;
        }
        return head;
    }
}
```



## 876. Middle of the Linked List

### Approach 1: Fast and slow pointer

Use two pointers `slow` and `fast` which initially points to the `head` node. The `fast` node traverses twice as fast as `slow`. When `fast` is null or `fast.next` is null, `slow` must be in the middle of the list.

- Time complexity: Θ(N), where N is the number of the nodes given in the list
- Space complexity: Θ(1), the space used by the two pointers

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
```

# 3 Queue

| No.                                  | Difficult | Tags               | Last Completed |
| ------------------------------------ | --------- | ------------------ | -------------- |
| 225. Implement Stack using Queues    | Easy      | Queue, Stack       | 2022-05-15     |
| 346. Moving Average from Data Stream | Easy      | Queue              | 2022-05-15     |
| 281. Zigzag Iterator                 | Medium    | Iterator           | 2022-05-16     |
| 1429. First Unique Number            | Medium    | Hash; Linked List  | 2022-05-19     |
| 54. Spiral Matrix                    | Medium    | Matrix; Simulation | 2022-05-19     |
| 59. Spiral Matrix II                 | Medium    | Matrix; Simulation | 2022-05-21     |
| 362. Design Hit Counter              | Medium    | Queue; Window      | 2022-05-21     |

## 54. Spiral Matrix

### Approach #1: Traverse layer by layer

We can iterate the matrix layer by later. The k-th layer means that the coordinate of its top-left element is (k, k). 

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        LinkedList<Integer> res = new LinkedList<>();
        int m = matrix.length, n = matrix[0].length;
        int top = 0, left = 0, bottom = m - 1, right = n - 1;
        int row, col;
        while (top <= bottom && left <= right) {
            for (col = left; col <= right; col++) {
                res.add(matrix[top][col]);
            }
            for (row = top + 1; row <= bottom; row++) {
                res.add(matrix[row][right]);
            }
            if (bottom > top && right > left) {
                for (col = right - 1; col >= left; col--) {
                    res.add(matrix[bottom][col]);
                }
                for (row = bottom - 1; row > top; row--) {
                    res.add(matrix[row][left]);
                }
            }
            top++;
            left++;
            bottom--;
            right--;
        }
        return res;
    }
}
```

## 59. Spiral Matrix II

### Approach #1: Traverse layer by layer

This approach is similar to that of 54. Spiral Matrix, except that top == left and bottom == right.

```java
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int num = 1;
        int top = 0, bottom = n - 1;
        int row, col;
        while (top <= bottom) {
            for (col = top; col <= bottom; col++) {
                res[top][col] = num++;
            }
            for (row = top + 1; row <= bottom; row++) {
                res[row][bottom] = num++;
            }
            if (top < bottom) {
                for (col = bottom - 1; col >= top; col--) {
                    res[bottom][col] = num++;
                }
                for (row = bottom - 1; row > top; row--) {
                    res[row][top] = num++;
                }
            }
            top++;
            bottom--;
        }
        return res;
    }
}
```

### Approach #2: Use direction vector

In approach #1, we use a separate loop for each direction. Here, we use an optimized traversal that can use only one loop for all directions. Specifically, we use an array `directions` to store the changes in x and y coordinates and a pointer `d` to mark the current direction.

```java
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int d = 0, num = 1;
        int row = 0, col = 0, nextRow, nextCol;
        while (num <= n * n) {
            res[row][col] = num++;
            nextRow = Math.floorMod(row + directions[d][0], n);
            nextCol = Math.floorMod(col + directions[d][1], n);

            if (res[nextRow][nextCol] != 0) {
                d = (d + 1) % 4;
            }

            row += directions[d][0];
            col += directions[d][1];
        }
        return res;
    }
}
```

Note:

- `Math.floorDiv(int x, int y)`: 返回最接近x/y的int值
- `Math.floorMod(int x, int y)`: floor modulus = `x - (floorDiv(x, y) * y)`，最后的结果一定和y同号
  - [形象解释](https://blog.csdn.net/scarecrow_fly/article/details/105834533)：计算一个时针的位置，且要将结果化为0~11之间的数。如果时针仅顺时针移动，则计算公式为`（position + adjustment) % 12`（记作eq1）。但是，如果时针逆时针移动，那么eq1可能会返回负值。此时，采用`floorMod(position + adjustment, 12)`可解决这个问题。

比较`%`（取余）和`floorMod`（取模）的区别：

- (-1) % 3 = -1
- floorMod(-1, 3) = 2

## 225. Implement Stack using Queues

### Approach 1: 2 queues, push - O(1), pop - O(n)

- `push(int x)`: Store the new element at the end of `q1`
- `pop()`: While doing the `pop()` operation, use `q2` as a temporary storage to enqueue the removed elements from `q1`, then swap the pointer `q1` and `q2`.
- Use a instance variable `top` to store the top element.

```java
class MyStack {
    Queue<Integer> q1;
    Queue<Integer> q2;
    int top;

    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        q1.add(x);
        top = x;
    }

    public int pop() {
        while (q1.size() > 1) {
            top = q1.remove();
            q2.add(top);
        }
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        return q2.remove();
    }

    public int top() {
        return top;
    }

    public boolean empty() {
        return q1.isEmpty();
    }
}
```

Complexity analysis:

| Operation        | Time complexity | Space complexity |
| ---------------- | --------------- | ---------------- |
| void push(int x) | Θ(1)            | Θ(1)             |
| int pop()        | Θ(n)            | Θ(1)             |
| int top()        | Θ(1)            | Θ(1)             |
| boolean empty()  | Θ(1)            | Θ(1)             |

### Approach 2: 2 queues, push - O(n), pop - O(1)

- `push(int x)`: Use `q2` to store the new elements. While `q1` is not empty, move the first element of `q1` to the rear of `q2`. After that, `q2` will store elements in a revered order, just like they are in a stack. Then swap the pointer `q1` and `q2`.
- `pop()`: Remove and return the first element of `q1`.

```java
class MyStack {
    Queue<Integer> q1;
    Queue<Integer> q2;
    int top;

    public q225b_MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        q2.add(x);
        top = x;
        while (!q1.isEmpty()) {
            q2.add(q1.remove());
        }
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    public int pop() {
        int res = q1.remove();
        if (!q1.isEmpty()) {
            top = q1.peek();
        }
        return res;
    }

    public int top() {
        return top;
    }

    public boolean empty() {
        return q1.isEmpty();
    }
}
```

Complexity analysis:

| Operation        | Time complexity | Space complexity |
| ---------------- | --------------- | ---------------- |
| void push(int x) | Θ(n)            | Θ(1)             |
| int pop()        | Θ(1)            | Θ(1)             |
| int top()        | Θ(1)            | Θ(1)             |
| boolean empty()  | Θ(1)            | Θ(1)             |

### Approach 3: 1 queue, push - O(n), pop - O(1)

This approach is an improved version of approach #2. In this approach, we use only one queue to store the elements in reversed order. To be specific, when we insert a new element, we move previous  elements to the back of the queue.

```java
class MyStack {
    Queue<Integer> q;

    public q225c_MyStack() {
        q = new LinkedList<>();
    }

    public void push(int x) {
        q.add(x);
        int size = q.size();
        for (int i = 1; i < size; i++) {
            q.add(q.remove());
        }
    }

    public int pop() {
        return q.remove();
    }

    public int top() {
        return q.peek();
    }

    public boolean empty() {
        return q.isEmpty();
    }
}
```

## 281. Zigzag Iterator

Note: this problem requires Premium in LeetCode but is free in [LintCode](https://www.lintcode.com/problem/540/).

### Approach #1: Two pointers

```java
import java.util.Iterator;
import java.util.List;

public class ZigzagIterator {
    Iterator<Integer> it1;
    Iterator<Integer> it2;
    int count;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        it1 = v1.iterator();
        it2 = v2.iterator();
        count = 0;
    }

    public int next() {
        count++;
        if (!it2.hasNext()) {
            return it1.next();
        }
        if (!it1.hasNext()) {
            return it2.next();
        }
        if ((count & 1) == 1) {
            return it1.next();
        } else {
            return it2.next();
        }
    }

    public boolean hasNext() {
        return it1.hasNext() || it2.hasNext();
    }
}

```

## 346. Moving Average from Data Stream

Note: this problem requires Premium in LeetCode but is free in [LintCode](https://www.lintcode.com/problem/642/description).

Complexity analysis:

- Time complexity: Θ(1)
- Space complexity: Θ(n)

### Approach #1: Use a queue

```java
class MovingAverage {
    Queue<Integer> queue;
    int size;
    double sum;

    public MovingAverage(int size) {
        queue = new LinkedList<>();
        this.size = size;
        sum = 0;
    }

    public double next(int val) {
        if (queue.size() == size) {
            sum -= queue.remove();
        }
        queue.add(val);
        sum += val;
        return  sum / queue.size();
    }
}
```

Note: you should set `sum` as `double` type, or you won't pass the complex test cases because it will cause algorithmic overflow （数值溢出）.

### Approach #2: Use a fixed-length array (circular queue)

```java
class MovingAverage {
    int[] window;
    int count;
    double sum;
    int i;

    public MovingAverage(int count) {
        window = new int[count];
        sum = 0;
        i = 0;
        this.count = 0;
    }

    public double next(int val) {
        if (count < window.length) {
            count--;
        }
        sum -= window[i];
        sum += val;
        window[i] = val;
        i = (i + 1) % window.length;
        return sum / count;
    }
}
```

## 362. Design Hit Counter

Note: this problem requires Premium in LeetCode but is free in [PepCoding](https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/stacks/design-hit-counter-official/ojquestion).

### Approach #1: Queue

Complexity analysis:

- Time complexity: O(n) 
- Space complexity: O(n), where n is the number of hits happening in last 5 minutes.

This solution has a problem. If there are large numbers of hits happen at the same timestamp, the queue will become very big. 

```java
class HitCounter {
    Queue<Integer> hits;

    /** Initialize your data structure here. */
    public HitCounter() {
        hits = new LinkedList<>();
    }

    /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        remove(timestamp);
        hits.offer(timestamp);
    }

    /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        remove(timestamp);
        return hits.size();
    }

    private void remove(int timestamp) {
        if (hits.isEmpty()) return;
        while (hits.peek() + 300 <= timestamp) {
            hits.poll();
        }
    }
}
```

### Approach #2: Window buckets

Use two arrays `times` and `hits` with a fixed size to store the timestamp and the count of hits respectively. 

- `hits(int timestamp)`: Use `timestamp % 300` to compute the index of the timestamp. If the timestamp is not equal to `times[index]`, then five minutes has passed. We should update `times[index ]` and set `hits[index]` as 1. Otherwise, `hits[index]` should add 1.

```java
class HitCounter {
    final int WINDOW_SIZE = 300;
    int[] times;
    int[] hits;

    /** Initialize your data structure here. */
    public HitCounter() {
        times = new int[WINDOW_SIZE];
        hits = new int[WINDOW_SIZE];
    }

    /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int index = timestamp % WINDOW_SIZE;
        if (times[index] == timestamp) {
            hits[index] += 1;
        } else {
            times[index] = timestamp;
            hits[index] = 1;
        }
    }

    /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int count = 0;
        for (int i = 0; i < WINDOW_SIZE; i++) {
            if (timestamp - times[i] < WINDOW_SIZE) {
                count += hits[i];
            }
        }
        return count;
    }
}
```

## 1429. First Unique Number in Data Stream

Note: this problem requires Premium in LeetCode but is free in [LintCode](https://www.lintcode.com/problem/685/).

### Approach #1

- Use a `Map<Integer, ListNode>` to store unique numbers in the form of Linked List. It should be noticed that the value should be its previous node so that we can easily delete this node when we find it is not unique.
- Use a pointer `head` to mark the first unique number so that we can return the first unique number in O(1) time.
- Use a pointer `tail` to mark the tail of the stored linked list. This pointer is helpful when we add a new node.
- Use a `Set<Integer>` to store duplicates

Complexity analysis:

- Time complexity: O(1) 
- Space complexity: O(n)

```java
class Solution {
    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        ListNode(int x, ListNode n) {
            val = x;
            next = n;
        }
    }

    private class DataStream {
        Map<Integer, ListNode> uniqueNumToPrev;
        ListNode head, tail;
        Set<Integer> duplicates;

        public DataStream() {
            uniqueNumToPrev = new HashMap<>();
            head = new ListNode(-1); // dummy head
            tail = head;
            duplicates = new HashSet<>();
        }

        public void add(int x) {
            if (duplicates.contains(x)) {
                return;
            }
            if (uniqueNumToPrev.containsKey(x)) {
                remove(x);
                duplicates.add(x);
            } else {
                ListNode node = new ListNode(x, null);
                uniqueNumToPrev.put(x, tail);
                tail.next = node;
                tail = node;
            }
        }

        private void remove(int x) {
            // remove the node from the linked list
            ListNode prev = uniqueNumToPrev.get(x);
            prev.next = prev.next.next;
            uniqueNumToPrev.remove(x);
            // change tail or prev of the next node
            if (prev.next == null) {
                tail = prev;
            } else {
                uniqueNumToPrev.put(prev.next.val, prev);
            }

        }

        public int firstUnique() {
            if (head.next != null) {
                return head.next.val;
            }
            return -1;
        }
    }

    public int firstUniqueNumber(int[] nums, int number) {
        DataStream ds = new DataStream();
        for (int i = 0; i < nums.length; i++) {
            ds.add(nums[i]);
            if (nums[i] == number) {
                return ds.firstUnique();
            }
        }
        return -1;
    }
}
```

# 4 Stack

| No.                                               | Difficult | Tags         | First Completed | Last Completed | 次数 |      |
| ------------------------------------------------- | --------- | ------------ | --------------- | -------------- | ---- | ---- |
| 155. Min Stack                                    | Easy      | Stack        | 2022-05-21      |                |      |      |
| 716. Max Stack                                    | Easy      | Stack        | 2022-05-22      |                |      |      |
| 232. Implement Queue using Stacks                 | Easy      | Stack, Queue | 2022-05-22      |                |      |      |
| 150. Evaluate Reverse Polish Notation             | Medium    | Stack        | 2022-05-24      |                |      |      |
| **224. Basic Calculator**                         | Hard      | Stack        | 2022-06-08      | 2022-07-17     | 3    |      |
| 227. Basic Calculator II【体感很难】              | Medium    | Stack        | 2022-06-09      | 2022-07-18     | 4    |      |
| 772. Basic Calculator III【巨难】                 | Hard      | Stack        | 2022-06-17      | 2022-07-18     | 5    |      |
| 770. Basic Calculator IV【无敌难，答案都看不懂】  | Hard      | Stack        | 2022-07-19      | 2022-07-20     | 1    |      |
| 20. Valid Parentheses                             | Easy      | Stack        | 2022-07-21      |                |      |      |
| 1472. Design Browser History                      | Medium    | Stack        | 2022-07-21      |                |      |      |
| 1209. Remove All Adjacent Duplicates in String II | Medium    | Stack        |                 | 2022-10-12     |      |      |
| 1249. Minimum Remove to Make Valid Parentheses    |           |              |                 |                |      |      |
| 735. Asteroid Collision                           |           |              |                 |                |      |      |

## 20. Valid Parentheses

```java
class Solution {
    public boolean isValid(String s) {
        if ((s.length() % 2) == 1) return false;
        HashMap<Character, Character> pairs = new HashMap<>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!pairs.containsKey(c)) {
                stack.push(c);
            } else if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
```

Complexity analysis:

- Time complexity: O(n) 
- Space complexity: O(n + |Σ|), |Σ|is the characters set, the size of which is 6 in this problem

## 150. Evaluate Reverse Polish Notation  

## 155. Min Stack

When we push a new element named `a` to the stack, we use an auxiliary stack to store the minimum value in the stack at this time.

Complexity analysis:

- Time complexity: O(1) 
- Space complexity: O(n)

```java
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
        minStack.add(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.add(val);
        minStack.add(Math.min(minStack.peek(), val));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

## 224. Basic Calculator

### Approach #1: Stack (iteration)

1. Use the `sign` with values {1, -1} to represent current operator. When we add a number, we add `sign * number`.
   - Note, the number may be more than 1 digit, so we should use all consecutive digits to make the number.
2. Use a stack `ops` to store signs, the top element of which is the outer sign for the current parenthesis. 
   - If we meet '(', we push `sign` to the top. 
   - If we meet ')', we pop the top element.
   - If we meet '+', `sign = ops.peek()`
   - If we meet '-', `sign = - ops.peek()`

```java
class Solution {
    public int calculate(String s) {
        int num = 0, res = 0;
        int sign = 1;
        Stack<Integer> stack = new Stack<>();
        stack.add(sign);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (i == s.length() - 1 || c != ' ' && !Character.isDigit(c)){
                res += sign * num;
                if (c == '+') {
                    sign = stack.peek();
                } else if (c == '-') {
                    sign = - stack.peek();
                } else if (c == '(') {
                    stack.push(sign);
                } else if (c == ')') {
                    stack.pop();
                }
                num = 0;
            }
        }
        return res;
    }
}
```

Complexity analysis:

- Time complexity: O(n) 
- Space complexity: O(n)

**反思**

这道题我原本是用递归做的，具体思路是，每当遇到`(`就`return res + calculate(s, i + 1)`。这样做是不对的，因为它改变了运算顺序——它会优先算s[1+1:]的值。例如，对于`(7)-(0)+(4)`，递归做法会先算`(0) + (4) = 4`，然后再算`7 - 4 = 3`，这相当于是在解`(7)-((0)+(4))`。

应该有可行的递归做法，但我目前还没想到。后面如果想到了再回来写题解。

## 227. Basic Calculator II

### Approach #1: Stack

As we know, '*' and '/' have higher precedence than '+' and '-'. We can first evaluate multiplications and divisions and store their values to the stack, then add all values to get the result.

Scan the input string from left to right:

1. If the current char is a digit, add it to the current number `num`
2. Otherwise, we look at the *operation* `op` previous to the current number:
   - '+' / '-' : We must evaluate the expression based on the next operation, so we just push the current number to the stack so that we can use it later.
   - '*' / '/': Pop the top value from the stack and evaluate the expression. 
3. Scan the stack and add all values.

:star: Note, `&&` has higher priority than `||`. For example, `s || q && r` equals `s || (q && r)`.

```java
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        char operation = '+';
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1){
                if (operation == '+') {
                    stack.push(num);
                } else if (operation == '-') {
                    stack.push(-num);
                } else if (operation == '*') {
                    stack.push(stack.pop() * num);
                } else if (operation == '/') {
                    stack.push(stack.pop() / num);
                }
                operation = c;
                num = 0;
            }
        }
        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }
}
```

Complexity analysis:

- Time complexity: O(n)
- Space complexity: O(n)

### Approach #2: Optimized approach without stack

In approach #1, we only use the stack's top element to evaluate multiplications and divisions. We can use a variable `res` beforehand and `prevNum` to keep track of the top element, thus eliminating the need for the stack.

```java
class Solution {
    public int calculate(String s) {
        char op = '+';
        int prevNum = 0, currNum = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = currNum * 10 + c - '0';
            }
            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1){
                if (op == '+' || op == '-') {
                    res += prevNum; // prevNum是op前的数字
                    prevNum = op == '+' ? currNum : -currNum;
                } else if (op == '*') {
                    prevNum *= currNum;
                } else if (op == '/') {
                    prevNum /= currNum;
                }
                op = c;
                currNum = 0;
            }
        }
        res += prevNum;
        return res;
    }
}
```

Complexity analysis:

- Time complexity: O(n)
- Space complexity: O(1)

## 232. Implement Queue Using Stacks

### Approach #1: push - O(n), pop - O(1)

- `push(int x)`: A queue is FIFO but a stack is LIFO. This means that we should push the newest element to the bottom of the stack. To do so, we first transfer all `s1` elements to the auxiliary stack `s2`, and add the newest element to `s1`, then pop all `s2` elements to `s1`. After doing this, the last elements is at the bottom of `s1`.

```java
class MyQueue {
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    int front;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        if (stack1.isEmpty()) {
            front = x;
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        stack1.push(x);
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
    }

    public int pop() {
        int res = stack1.pop();
        if (!stack1.empty()) {
            front = stack1.peek();
        }
        return res;
    }

    public int peek() {
        return front;
    }

    public boolean empty() {
        return stack1.isEmpty();
    }
}
```

Complexity analysis:

| Operation        | Time complexity | Space complexity |
| ---------------- | --------------- | ---------------- |
| void push(int x) | Θ(n)            | Θ(n)             |
| int pop()        | Θ(1)            | Θ(1)             |
| int top()        | Θ(1)            | Θ(1)             |
| boolean empty()  | Θ(1)            | Θ(1)             |

### Approach #2: push - O(1), pop - amortized O(1)

- `push(int x)`: always put the last element on top of `s1`
- `pop()`: pop all elements from `s1` to `s2` then return the top element of `s2`.

```java
class MyQueue {
    Stack<Integer> stack1;
    Stack<Integer> stack2;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        stack1.push(x);
    }

    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public int peek() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }

    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
}
```

Complexity analysis:

| Operation        | Time complexity                 | Space complexity |
| ---------------- | ------------------------------- | ---------------- |
| void push(int x) | Θ(1)                            | Θ(n)             |
| int pop()        | Amortised Θ(1), worst-case Θ(n) | Θ(1)             |
| int top()        | Θ(1)                            | Θ(1)             |
| boolean empty()  | Θ(1)                            | Θ(1)             |

## 716. Max Stack

This problem is free in [LintCode](https://www.lintcode.com/problem/859/).

This problem is similar to the 115. Min Stack except for `popMax()`.

Complexity analysis:

- Time complexity: worst-case for popMax() is Θ(n) 
- Space complexity: O(n)

```java
import java.util.Stack;

public class MaxStack {

    Stack<Integer> stack;
    Stack<Integer> maxStack;

    public q716_MaxStack() {
        stack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        if (maxStack.isEmpty() || maxStack.peek() <= x) {
            maxStack.push(x);
        } else {
            maxStack.push(maxStack.peek());
        }
    }

    public int pop() {
        maxStack.pop();
        return stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int peekMax() {
        return maxStack.peek();
    }

    public int popMax() {
        int max = peekMax();
        Stack<Integer> buffer = new Stack<>();
        while (top() != max) {
            buffer.push(pop());
        }
        pop();
        while (!buffer.isEmpty()) {
            push(buffer.pop());
        }
        return max;
    }
}
```

## 735. Asteroid Collision

Loop through the asteroids, and use a stack to store the stable asteroids. Say we have a stack with the rightmost asteroid `top`, and a `new` asteroid comes in. 

- If `top` moves right (+) and `new` moves left (-), they will collide. 

  - If the size of `new` > `top`, `top` will blow up, then we need to check if `new` will collide with next `top`. In the next round, `new` and `top` might meet other conditions.

  - if `new` == `top`, both will blow up. We will stop checking

  - if `new` < `top`, `new` will blow up. We will stop checking

- else: push `new` to the stack


```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            collision: { 
                while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
                    if (-asteroid > stack.peek()) {
                        stack.pop(); 
                        continue; 
                    } else if (-asteroid == stack.peek()) {
                        stack.pop();
                    } 
                    break collision;
                }
                stack.push(asteroid);
            }
        }
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }
}
```

## 770. Basic Calculator IV

太难了，光是理解答案就花了一天……先跟着答案敲一遍代码，后面再慢慢理解吧

## 772.Basic Calculator III

Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open `(` and closing parentheses `)`, the plus `+` or minus sign `-`, non-negative integers and empty spaces ` `.

The expression string contains only non-negative integers, `+`, `-`, `*`, `/` operators , open `(` and closing parentheses `)` and empty spaces ` `. The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of `[-2147483648, 2147483647]`.

Some examples:

```
"1 + 1" = 2
" 6-4 / 2 " = 4
"2*(5+5*2)/3+(6/2+8)" = 21
"(2+6* 3+5- (3*14/7+2)*5)+3"=-12
```

Note: Do not use the `eval` built-in library function.



This problem is free in [LintCode](https://www.lintcode.com/problem/849/solution/18404).（但是test cases不如leetcode全面）

### Approach #1: recursion

本题在227. Basic Calculator II基础上增加了括号，我们可以在227的题解上增加对括号的处理。具体方法为：如果遇到左括号，则寻找右括号的位置（这里用了一个trick，详情请看代码），然后取出这段substring进行递归。

```java
class Solution {
    public int calculate(String s) {
        int prevNum = 0, currNum = 0, res = 0;
        char op = '+';
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    currNum = currNum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            } else if (c == '(') {
                int start = i, count = 1;
                while (count > 0) {
                    i++;
                    if (s.charAt(i) == ')') {
                        count--;
                    } else if (s.charAt(i) == '(') {
                        count++;
                    }
                }
                currNum = calculate(s.substring(start + 1, i));
            }
            if (i == s.length() - 1 || c != ' ' && !Character.isDigit(c)) {
                if (op == '+' || op == '-') {
                    res += prevNum; //prevNum是op前面的数字
                    prevNum = op == '+' ? currNum : -currNum;
                } else if (op == '*') {
                    prevNum *= currNum;
                } else if (op == '/'){
                    prevNum /= currNum;
                }
                op = c;
            }
            i++;
        }
        res += prevNum;
        return res;
    }
}
```

Complexity analysis:

- Time complexity: 
  - worst-case: O(n<sup>2</sup>). 本算法每次碰到左括号，都会对括号部分进行遍历。如果该算式有多层嵌套的括号，如(1 +(2 + (3)))，那么每一层括号都会被重新遍历一次，这时该算法的复杂度最大可为O(n<sup>2</sup>)。 
  - best: O(n). 没有括号的情况
- Space complexity: 假设该算式有m层括号，那么该算法会递归m次，而且每一次递归都要取一次substring
  - worst-case: O(n<sup>2</sup>)
  - best: O(1)

### Approach #2: Two stacks

本题相当于是224. Basic Calculator I和227. Basic Calculator II的结合。224用了一个栈存储符号，227的Approach#1则用了一个栈存储数字，因此，在解本题时，我们可以使用两个栈，一个`nums`储存符号，一个`ops`储存数字。

对于s[i]:

- 如果是数字，那么则找全数字，压入`nums`
- 如果是左括号，直接压入`ops`
- 如果是运算符：如果当前的操作符比`ops`栈顶的优先级小，则可以进行运算（另写函数`operate`），然后将得到的数压入`nums`
- 如果是右括号，对栈顶的算式进行运算，直至`ops`栈顶为左括号

参考题解：https://www.youtube.com/watch?v=YazIB0OZBoI

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Solution {
    Map<Character, Integer> precedence;

    public Solution() {
        precedence = new HashMap<>();
        precedence.put('(', -1); // 例，1 * (3 -，这种情况不能运算
        precedence.put('+', 0);
        precedence.put('-', 0);
        precedence.put('*', 1);
        precedence.put('/', 1); 
    }

    public int calculate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    i++;
                    num = num * 10 + s.charAt(i) - '0';
                }
                nums.push(num);
            } else if (c == '(') {
                ops.push('(');
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    nums.push(operate(nums, ops));
                }
                ops.pop();
            } else if (c != ' ') {
                // 若当前运算符的优先级<=上一个运算符，如 1 * 2 +，则上一个运算符可以进行运算
                // 这里必须用while，不能用if
                // 因为operate是从后往前算的，如果不把前面能算的先算了，最后可能导致运算顺序改变
                // 例如，对于1*2-3*4+5*6, 使用if相当于计算1*2-(3*4+5*6)=-40
                while (!ops.isEmpty() && compare(c, ops.peek()) <= 0) {
                    nums.push(operate(nums, ops));
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) {
            nums.push(operate(nums, ops));
        }
        return nums.pop();
    }

    private int operate(Stack<Integer> nums, Stack<Character> ops) {
        int a = nums.pop();
        int b = nums.isEmpty() ? 0 : nums.pop();
        char op = ops.pop();
        switch (op) {
            case '+': return b + a;
            case '-': return b - a;
            case '*': return b * a;
            case '/': return b / a;
            default: return 0;
        }
    }

    private int compare(char op1, char op2) {
        return precedence.get(op1) - precedence.get(op2);
    }
}
```

Complexity analysis:

- Time complexity: O(n)
- Space complexity: O(n)

## 1209. Remove All Adjacent Duplicates in String II

Approach #3 and Approach #5 is the most efficient.

### Approach #1: Brute Force

- Loop through the string, if current character equals the previous one, `count++`; else `count = 1`.
- if `count == k`, delete the substring, reset i as 0 and count as 1.

1. java: 

   ```java
   class Solution {
       public String removeDuplicates(String s, int k) {
           StringBuilder sb = new StringBuilder(s);
           int count = 0;
           for (int i = 1; i < sb.length(); i++) {
               if (sb.charAt(i) == sb.charAt(i - 1)) {
                   count++;
               } else {
                   count = 1;
               }
               if (count == k) {
                   sb.delete(i - k + 1, i + 1);
                   i = 0;
                   count = 1;
               }
           }
           return sb.toString();
       }
   }
   ```

2. c++:

   ```c++
   class Solution {
   public:
       string removeDuplicates(string s, int k) {
           int count = 1;
           for (int i = 1; i < s.length(); i++) {
               if (s[i] == s[i - 1]) {
                   count++; 
               } else {
                   count = 1;
               }
               if (count == k) {
                   s.erase(i - k + 1, k);
                   i = 0; // i will become 1 after stepping through 'for (int i = 1; i < s.length(); i++)'
                   count = 1;
               }
           }
           return s;
       }
   };
   ```

Complexity analysis:

- Time complexity: O(n<sup>2</sup>). We'll meet an Time Limit Exceeded error with this approach.
- Space complexity: O(n)

### Approach #2

In approach #1, every time after we delete a substring, we should restart from the begining. That's why approach #1 is not efficient. To improve this issue, we use an array `counts` to record the consecutive occurences of each character:

- If s[i] == s[i - 1], count[i] == count[i - 1] + 1; else count[i] == 1
- if count[i] == k, delete the substring, and set `i` as `i - k`.

```java
class Solution {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        int counts[] = new int[s.length()];
        for (int i = 0; i < sb.length(); i++) {
            if (i > 0 && sb.charAt(i) == sb.charAt(i - 1)) {
                counts[i] = counts[i - 1] + 1;
            } else {
                counts[i] = 1;
            }
            if (counts[i] == k) {
                sb.delete(i - k + 1, i + 1);
                i -= k;
            }
        }
        return sb.toString();
    }
}
```

Complexity analysis:

- Time complexity: O(n)
- Space complexity: O(n)

### Approach #3: Stack

In approach #2, deleting a substring from the middle of a StringBuilding requires O(n) operations, but deleting from the end only requires O(1) operation. Now, we use a stack to store characters.

#### Method 1: StringBuilder (functions as a stack) + Stack\<Integer>

```java
class Solution {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> counts = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == s.charAt(i)) {
                counts.push(counts.pop() + 1);
            } else {
                counts.push(1);
            }
            sb.append(s.charAt(i));
            if (counts.peek() == k) {
                counts.pop();
                sb.delete(sb.length() - k, sb.length());
            }
        }
        return sb.toString();
    }
}
```

#### Method 2: Stack\<Pair>

This method is more elegant.

```java
class Solution {
  class Pair {
        char ch;
        int count;

        Pair(char c, int i) {
            ch = c;
            count = i;
        }
    }

    public String removeDuplicates(String s, int k) {
        Stack<Pair> counts = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (!counts.isEmpty() && s.charAt(i) == counts.peek().ch) {
                counts.peek().count += 1;
            } else {
                counts.push(new Pair(s.charAt(i), 1));
            }
            if (counts.peek().count == k) {
                counts.pop();
            }
        }
        StringBuilder sb = new StringBuilder();
        // Stack is implemented by array, so we can use for-each to count from the bottom to the top
        for (Pair p : counts) {
            for (int i = 0; i <p.count; i++) {
                sb.append(p.ch);
            }
        }
        return sb.toString();
    }
}
```

### Approach #4: Two pointers

As said in Approach #3, deleting from the middle of a StringBuilder requires O(n) operations. Now, instead of deleting the substring, we use two pointers to mark the rest of the string. We use the fast pointer to loop through the string, and we use the slow pointer to mark current left substring. Everytime we delete k characters, slow should be deducted by k.

<img src="./assets/1209_approach5.png" alt="img" style="zoom:60%;" />

```java
class Solution {
  public String removeDuplicates(String s, int k) {
        Stack<Integer> stack = new Stack<>();
        char sa[] = s.toCharArray();
        int slow = 0;
        for (int i = 0; i < s.length(); i++, slow++) {
            sa[slow] = sa[i];
            if (slow > 0 && sa[slow] == sa[slow - 1]) {
                stack.push(stack.pop() + 1);
            } else {
                stack.push(1);
            }
            if (stack.peek() == k) {
                slow -= k;
                stack.pop();
            }
        }
        return new String(sa, 0, slow);
    }
}
```

## 1249. Minimum Remove to Make Valid Parentheses

### Approach #1: Stack + StringBuilder

Use stack to store the index to `(`, and use a hashset to store the index to remove. Every time we meet a `)`, we check if there's a `(` in stack. If not, we should delete this `)` (add its index to the set). After finishing looping through the string, if the stack is not empty, the `(` in the stack should be deleted.

```java
class Solution {
  public String minRemoveToMakeValid(String s) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> indexToRemove = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    indexToRemove.add(i);
                }
            }
        }
        while (!stack.isEmpty()) {
            indexToRemove.add(stack.pop());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!indexToRemove.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
```

Complexity analysis:

- Time complexity: O(n). To be specific, 3n for 3 loops, and 1n for `sb.toString()`. Therefore, the total complecity is 4n.
- Space complexity: O(n)

### Approach #2: Scan the string for 2 times

1. Scan the string forward, delete all invalid `)`.
2. Scan the string backward, delete all invaild `(`.

`CharSequence` is a interface, `String` and `StringBuilder` both implements this interface.

```java
class Solution {
  private StringBuilder removeInvalidClosing (CharSequence s, char open, char close) {
        int balance = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == open) {
                balance++;
            } else if (c == close) {
                if (balance == 0) continue;
                balance--;
            }
            sb.append(c);
        }
        return sb.reverse();
    }

    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = removeInvalidClosing(s, '(', ')');
        StringBuilder res = removeInvalidClosing(sb, ')', '(');
        return res.toString();
    }
}
```

Complexity analysis:

- Time complexity: O(n). To be specific,  the total complecity is 4n.
- Space complexity: O(n), but is smaller than approach #1. Because approach #2 has no hashmap or stack.

### Approach #3: improved approach #2

When scanning backward, just remove the rightmost `(`.

```java
class Solution {
  public String minRemoveToMakeValid(String s) {
        // 1. scan forward, delete invalid )
        int balance = 0, openCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                openCount++;
                balance++;
            } else if (c == ')') {
                if (balance == 0) continue;
                balance--;
            }
            sb.append(c);
        }
        // 2. remove the rightmost (
        StringBuilder res = new StringBuilder();
        int openKeep = openCount - balance;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(') {
                if (openKeep == 0) continue;
                openKeep--;
            }
            res.append(sb.charAt(i));
        }
        return res.toString();
    }
}
```

Complexity analysis:

- Time complexity: O(n). To be specific,  the total complecity is 3n.
- Space complexity: O(n), To be specific,  the space complecity is 2n.

## 1381. Design a Stack with Increment Operation

I encountered the same problem in Lucid Software's OA, but the OA problem is more challenging. First, the OA problem has higher requirement for time efficiency. You must complete 2*10<sup>5</sup> operations in less then 4 seconds, so your `inc` operation must be O(1). Second, the range of increment value is -1\*10<sup>9</sup>~1\*10<sup>9</sup>. To avoid integer overflow, we should use long array instead of int array.

### Naive Approach: inc O(k)

```java
class CustomStack {
    int[] stack;
    int size;

    public CustomStack(int maxSize) {
        stack = new int[maxSize];
        size = 0;
    }
    
    public void push(int x) {
        if (size < stack.length) {
            stack[size++] = x;
        }
    }
    
    public int pop() {
        if (size == 0) {
            return -1;
        }
        return stack[--size];
    }
    
    public void increment(int k, int val) {
        int limit = Math.min(k, size);
        for (int i = 0; i < limit; i++) {
            stack[i] += val;
        }
    }
}
```

Time complexity:

- `push(int x)`: O(1)
- `pop()`: O(1)
- `Inc(int k, int val)`: O(k)

### Advanced Approach: inc O(1)

To reduce the time complexity of `void inc(int k, int val)`  to O(1), we use an int array `add` to record the `inc` operations. However, instead of increasing bottom `k` elements right away, we only use `add[k - 1] += val` to make a note. When we execute `pop()`, the returned value should be `stack[size - 1] + add[size - 1]`, then we move the not to the value below, so `add[size - 2] += add[size - 1]`,  

```java
class CustomStack {
    int[] stack;
    int[] add;
    int size;

    public CustomStack(int maxSize) {
        stack = new int[maxSize];
        add = new int[maxSize];
        Arrays.fill(stack, 0);
        Arrays.fill(add, 0);
        size = 0;
    }
    
    public void push(int x) {
        if (size < stack.length) {
            stack[size] = x;
            size++;
        }
    }
    
    public int pop() {
        if (size == 0) {
            return -1;
        }
        int res = stack[size - 1] + add[size - 1];
        if (size > 1) {
            add[size - 2] += add[size - 1];
        }
        add[size - 1] = 0;
        size--;
        return res;
    }
    
    public void increment(int k, int val) {
        int limit = Math.min(k - 1, size - 1);
        if (limit >= 0) {
            add[limit] += val;
        }
    }
}
```

Time complexity: the amortized complexity for each operation is O(1) 

## 1472. Design Browser History

### Approach #1: ArrayList

There are many ways to solve this problem, such as using two stacks to store the websites: one for history, one for future. But I think the simplest way is to use `ArrayList`, where we can access any element or remove last element in O(1) time.

```java
class BroswerHistory {
    ArrayList<String> history;
    int curr;

    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        curr = 0;
    }

    /**
     * Visits url from the current page.
     * It clears up all the forward history.
     */
    public void visit(String url) {
        while (curr < history.size() - 1) {
            history.remove(history.size() - 1);
        }
        history.add(url);
        curr++;
    }

    public String back(int steps) {
        curr = Math.max(0, curr - steps);
        return history.get(curr);
    }

    public String forward(int steps) {
        curr = Math.min(history.size() - 1, curr + steps);
        return history.get(curr);
    }
}
```

Complexity analysis:

- Time complexity: 
  - visit: worst O(n), best O(1)
  - back & forward: O(1)
- Space complexity: O(m) for m websites

# 5 Hash

## 146. LRU Cache

In this problem, we want to get and put key-value pair in O(1) time, so we need to use **HashMap**. On the other hand, we want to record the used history of each key, so we can maintain a **double linked list** which allows us to insert data into the head and tail. In Java, [LinkedHashMap](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html) has such function.

### Approach #1: DIY

Use a customized class `Node` to store each key-value pair and their order and make them a double linked list. Use `HashMap<Integer, Node>` to store the key and the address of each key-value pair.

1. `get`: use hashmap to find the node, then move the node to the head
2. `put`: use hashmap to find the node
   - if the node exists: update the value
   - else:
     - if the map is full: remove the tail from the linked list and map, then insert the new node to the head
     - Else: insert the new node to the head

```java
class LRUCache {
  private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    int capacity;
    Map<Integer, Node> cache;
    Node sentinel;

    public LRUCache(int cap) {
        capacity = cap;
        cache = new HashMap<>();
        sentinel = new Node(-1, -1);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            moveToHead(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node node = new Node(key, value);
            addHead(node);
            cache.put(key, node);
            if (cache.size() > capacity) {
                Node last = removeLast();
                cache.remove(last.key);
            }
        }
    }

    private void addHead(Node node) {
        node.prev = sentinel;
        node.next = sentinel.next;
        sentinel.next = node;
        node.next.prev = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addHead(node);
    }

    private Node removeLast() {
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        return last;
    }
}
```

### Approach #2: use LinkedHashMap

```java
class LRUCache extends LinkedHashMap<Integer, Integer> {
  	int capacity;

    public q146b_LRUCache(int c) {
        super(c, 0.75f, true); // true for accessOrder, false for insertion order
        this.capacity = c;
    }

    public int get(int key) {
        return super.get(key);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
```

## 128 Longest Consecutive Sequence

### Approach #1: Sort

Sort the nums in place, then count consequtive numbers. It should be noted that if the same number occurs multiple times, we just skip the occurances after the first time.

```java
class Solution {
  	public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);
        int longest = 0, count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                if(nums[i] == nums[i - 1] + 1) {
                    count++;
                } else {
                    longest = Math.max(longest, count);
                    count = 1;
                }
            }
        }
        return Math.max(longest, count);
    }
}
```

Time complexity: O(NlogN)

### Approach #2: HashSet

Use `HashSet` to store the numbers in the array. Iterating over the numbers, and find the highest consecutive number of the current number, then update the result. To improve time efficiency, we only attempt to build sequences from the numbers that are not already part of a longer sequence. To implement this goal, we just check if the `HashSet` contains `currentNum - 1`.

```java
class Solution {
  	public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Set<Integer> numSet = new HashSet<>();
        for (int n : nums) {
            numSet.add(n);
        }
        int longest = 0, count = 1;
        for (int num : nums) {
            if (!numSet.contains(num - 1)) {
                int currNum = num;
                while (numSet.contains(++currNum)) {
                    count++;
                }
                longest = Math.max(longest, count);
                count = 1;
            }
        }
        return Math.max(longest, count);
    }
}
```

Time complexity: O(N)

# 6 Heap

# 7 Binary Search

# 8 Two Pointers

# 9 BFS

# 10 DFS

# 11 Prefix Sum

# 12 Union Find

# 13 Monotone Stack／Queue

# 14 Sweep Line

# 15 TreeMap

# 16 Dynamic Programming

