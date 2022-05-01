reference: https://zhuanlan.zhihu.com/p/349940945

# Sort

- 基础知识：快速排序（Quick Sort）， 归并排序（Merge Sort）的原理与代码实现。需要能讲明白代码中每一行的目的。快速排序时间复杂度平均状态下O（NlogN），空间复杂度O（1），归并排序最坏情况下时间复杂度O（NlogN），空间复杂度O（N）

| No.                            | Difficult | Tags                    | Last Completed | High-F |
| ------------------------------ | --------- | ----------------------- | -------------- | ------ |
| 148. Sort List                 | Medium    | Merge Sort, Linked List | 2022-04-27     |        |
| 56. Merge Intervals            | Medium    | Sort                    | 2022-05-01     |        |
| 27. Remove Elements            | Easy      | Two pointers            | 2022-05-01     |        |
| 179. Largest Number            | Medium    | String, Comparator      | 2022-05-01     |        |
| 75. Sort Colors                |           |                         |                |        |
| 215. Kth Largest Element       |           |                         |                |        |
| 4. Median of Two Sorted Arrays |           |                         |                |        |
|                                |           |                         |                |        |
|                                |           |                         |                |        |

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

## 148. Sort List

### Approach 1: merge sort

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

# Linked List

| No.                            | Difficult | Tags                      | Last Completed | High-F |
| ------------------------------ | --------- | ------------------------- | -------------- | ------ |
| 876. Middle of the Linked List | Easy      | Linked List, Two Pointers | 2022-04-27     |        |
|                                |           |                           |                |        |
|                                |           |                           |                |        |
|                                |           |                           |                |        |

## 876. Middle of the Linked List

### Approach 1: fast and slow pointer

Use two pointers `slow` and `fast` which initially points to the `head` node. The `fast` node traverses twice as fast as `slow`. When `fast` is null or `fast.next` is null, `slow` must be in the middle of the list.

- Time complexity: Θ(N), where N is the number of the nodes given in the list
- Space coplexity: Θ(1), the space used by the two pointers

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



