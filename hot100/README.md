[TOC]



# 4. 寻找两个正序数组的中位数

给定两个大小分别为 `m` 和 `n` 的正序（从小到大）数组 `nums1` 和 `nums2`。请你找出并返回这两个正序数组的 **中位数** 。

## 法一：归并排序

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



## ※法二：二分查找

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





# 5. 最长回文子串

## 法一：动态规划

令`P(i, j)`表示字符串 `s[i...j]` 是否为回文串：

- 如果 `i == j`：`P(i, j) = true`
- 如果 `j == i + 1`：
  - 如果`Si == Sj`，则`P(i, j) = true`
  - 否则`P(i, j) = false`
- 其它情况下，`P(i, j) = P(i + 1, j - 1) ∧ (Si == Sj)`，

则`P(i, j)`的状态转移方程为：
$$
P(i, j)=
\begin{cases}
true,& \text{i == j}\\
Si == Sj, & \text{j == i + 1}\\
P(i + 1, j - 1) ∧ (Si == Sj),&  \text{j > i + 1}\\
\end{cases}
$$

记录下`P(i, j)`为`true`且长度最大时的i和j，即可得到最长回文子串。

例如：对于字符串“babad”，其动态规划的备忘录如下。其中粉色箭头为填充顺序：从(0,0)开始，按对角线从左上到右下填充；然后是(0, 1)开始的对角线，(0,2) ...

<img src="C:\Users\Sonia\AppData\Roaming\Typora\typora-user-images\image-20210511153545166.png" alt="image-20210511153545166" style="zoom:33%;" />

```java
class Solution {
	public String longestPalindrome(String s) {
        if (s.length() < 2) {
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) { //所有长度为1的子串都是回文子串
            dp[i][i] = true;
        }
        int maxLen = 1, start = 0;
        for (int j = 1; j < s.length(); j++) { // j + 1为子串长度
            for (int i = 0; i + j < s.length(); i++) { // i为左边界，i + j为右边界
                if (s.charAt(i) != s.charAt(i + j)) {
                    dp[i][i + j] = false;
                } else {
                    if (j == 1) {
                        dp[i][i + j] = true;
                    } else {
                        dp[i][i + j] = dp[i + 1][i + j - 1];
                    }
                }
                if (dp[i][i + j] && j + 1 > maxLen) {
                    maxLen = j + 1;
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
}
```



# 法二：中心扩展法

可以发现，`P(i, j)`的**状态转移链**为：
$$
边界情况 → ... → P(i - 2, j + 2) → P(i - 1, j + 1) → P(i, j)
$$
上面的“边界情况”指的是P(i, i) 或P(i, i + 1），这里我们称为“回文中心”。因此，这个算法也叫做“中心扩展法”。

对应下图中，状态转移链即为从`(i, i)` 或`(i, i + 1)`沿着对角线从左下到右上逐步计算回文长度。

注意：如果`P(i, j)`为非回文，则不必继续计算`P(i + 1, j - 1)`及之后的情况，因为它们一定为非回文。

<img src="C:\Users\Sonia\AppData\Roaming\Typora\typora-user-images\image-20210511154603366.png" alt="image-20210511154603366" style="zoom:33%;" />

找到状态转移链后，就可以不用备忘录记录每个`P(i, j)`的值了，所以此方法本质上是在法一的基础上去掉了备忘录。

```java
class Solution {
    public String longestPalindrome(String s) {
        int maxLen = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                start = i - (len - 1) / 2;
                maxLen = len;
            }
        }
        return s.substring(start, start + maxLen);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
```

