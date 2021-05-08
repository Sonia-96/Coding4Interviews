[TOC]



# 4. 寻找两个正序数组的中位数

给定两个大小分别为 `m` 和 `n` 的正序（从小到大）数组 `nums1` 和 `nums2`。请你找出并返回这两个正序数组的 **中位数** 。

## 方法一：归并排序

对两个数组进行归并排序，然后返回合并后的数组的中位数

- 时间复杂度：Θ(m + n)
- 空间复杂度：Θ(m + n)

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
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
}
```



优化：无需记录合并后的数组，只需找到第k大的数即可。

- 时间复杂度：Θ(m + n) （确切的说是Θ((m + n) / 2))
- 空间复杂度：Θ(1)

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2;
        }
    }

    private double getKthElement(int[] nums1, int[] nums2, int k) {
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
}
```



## ※方法二：二分查找

令原数组为A和B，由于A和B都是排好序的，所以在查找中位数时可以使用**二分查找**。

- 时间复杂度：Θ(log(m + n)) 
- 空间复杂度：Θ(1)



假设两个数组的长度分别为m和n：

- 如果m+n是奇数，则中位数为第`(m + n) / 2 + 1`个元素
- 如果m+n是偶数，则中为数位第`(m + n ) / 2`和`(m + n ) / 2 + 1`个元素

于是，本题可转化为<u>找到两个数组中第`k`小的元素</u>（或者说要排除前面`k-1`个元素）。



使用二分查找找到第`k`小的元素：

- 在数组A和B分别设置指针`p1`和`p2`，其初始值均为0

- 比较`A[p1 + k/2 - 1]` 和 `B[p2 + k/2 - 1]`的大小，其较小值一定不是中位数（因为最多只有`k - 2`个数比它小），所以较小值及其前的数都会被排除。
  - 特殊情况：p + k/2 - 1可能**越界**
  - 更新较小值所在数组的指针：p = 较小值下标 + 1
- 更新k值：k = k - 排除的元素个数
- 当k==1时，返回`A[p1]`和`B[p2]`中的较小值

```java
class SOlution {
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
            newP1 = p1 + Math.min(k / 2, nums1.length) - 1;
            newP2 = p2 + Math.min(k / 2, nums2.length) - 1;
            if (nums1[newP1] <= nums2[newP2]) {
                k -= (newP1 - p1 + 1);
                p1 = newP1 + 1;
            } else {
                k -= (newP2 - p2 + 1);
                p2 = newP2 + 1;
            }
        }
    }
}
```







