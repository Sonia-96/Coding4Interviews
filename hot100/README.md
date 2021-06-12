| 题目                                                        | 难度 | 知识点                     |
| ----------------------------------------------------------- | ---- | -------------------------- |
| 1. 两数之和                                                 | 简单 | 哈希                       |
| [2. 两数相加](#2. 两数相加)                                 | 中等 |                            |
| [3. 无重复字符的最长子串](#3. 无重复字符的最长子串)         | 中等 | 动态规划；滑动窗口；双指针 |
| [4. 寻找两个正序数组的中位数](#4. 寻找两个正序数组的中位数) | 困难 | 二分查找                   |
| [5. 最长回文子串](#5. 最长回文子串)                         | 中等 | 动态规划及其优化           |
| [78. 子集](#78. 子集)                                       | 中等 | 位操作；DFS；回溯法        |
| 10. 正则表达式匹配                                          | 困难 | 递归；动态规划             |
| 11. 盛最多水的容器                                          | 中等 | 双指针法                   |
| 15. 三数之和                                                | 中等 | 双指针法                   |
|                                                             |      |                            |
|                                                             |      |                            |
|                                                             |      |                            |

刷题网站：https://leetcode-cn.com/problem-list/2cktkvj/

注：标注“※”的方法时间效率上高于同一题目的其它方法。

# 1. 两数之和

## 哈希

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // key: num, value: index
        Map<Integer, Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[] {hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return null;
    }
}
```



# 2. 两数加和

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(-1), p = root; // 首结点为哨兵结点
        int carry = 0;
        while (l1 != null || l2 != null || carry == 1) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            sum = sum % 10;
            p.next = new ListNode(sum);
            p = p.next;
        }
        return root.next;
    }
}
```





# 3. 无重复字符的最长子串

## 法一：动态规划 + 哈希

定义f(i)为以第i个字符结尾的无重复字符的子串的最长长度。

- 使用哈希表记录每个字符上一次出现的位置，如果未出现则记为-1
  - 哈希表长度视字符范围而定：
    - ASCII: 128

- 如果第i个字符之前没出现过：f(i) = f(i-1) + 1

- 如果第i个字符之前出现过：假设上一次出现和这一次出现的位置的距离为d
  - 如果d <= f(i-1)：这个字符上一次出现在f(i-1)对应的最长子串中，则f(i) = d
  - 如果d > f(i-1)：这个字符上一次出现在f(i-1)对应的最长子串的前面，则f(i)=f(i-1)+1

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] prevIndex = new int[128];
        Arrays.fill(prevIndex, -1);
        int maxLen = 0, curLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i - prevIndex[s.charAt(i)] > curLen) {
                curLen += 1;
            } else {
                curLen = i - prevIndex[s.charAt(i)];
                maxLen = Math.max(maxLen, curLen);
            }
            prevIndex[s.charAt(i)] = i;
        }
        return Math.max(maxLen, curLen);
    }
}
```

- 时间复杂度：Θ(N)
- 空间复杂度：Θ(|Σ|)

## 法二：滑动窗口

设置一个窗口，一开始窗口左界为0，右界为-1（即窗口长度为0）。用`Set`记录窗口内的字符，如果下一个字符不在窗口内，则将下一个字符放入窗口内，即窗口右界+1；否则窗口左界右移至下一个字符不在窗口内为止。一直向右滑动窗口，直至窗口左界=`s.length() - 1`。最后返回滑动过程中窗口的最大长度。

```java
class Solution {
    public int lengthOfLongestSubstring2(String s) {
        HashSet<Character> occ = new HashSet<>();
        int right = -1, maxLen = 0;
        for (int left = 0; left < s.length(); left++) {
            while (right + 1 < s.length() && !occ.contains(s.charAt(right + 1))) {
                right += 1;
                occ.add(s.charAt(right));
            }
            maxLen = Math.max(occ.size(), maxLen);
            occ.remove(s.charAt(left));
        }
        return maxLen;
    }
}
```

- 时间复杂度：Θ(N)
- 空间复杂度：Θ(|Σ|)

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
        if ((nums.length & 1) == 1) {
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

例如：对于字符串“babad”，其动态规划的备忘录如下。其中粉色箭头为备忘录的填充顺序：从(0,0)开始，按对角线从左上到右下填充；然后是(0, 1)开始的对角线，(0,2) ...

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



## ※法二：中心扩展法

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



# 6. Z字形变换

将一个给定字符串 `s` 根据给定的行数 `numRows`，以从上往下、从左到右进行 Z 字形排列。

比如输入字符串为 "`PAYPALISHIRING`" 行数为 3 时，排列如下：

```
P   A   H   N
A P L S I I G
Y   I   R
```

之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："`PAHNAPLSIIGYIR`"。

## ※法一：推Z字形排序中字符下标的通项公式

按照与逐行读取Z字形图案相同的顺序访问字符串。

以`numRows = 5`为例：（下面`i`为行号）

```
【i = 0】 0       8             17
【i = 1】 1     7 9          16 18
【i = 2】 2   6   10       15   19
【i = 3】 3 5     11    14      ...
【i = 4】 4       12 13
```

- 第0行：字符的下标为`(2n - 2) * k`（为了方便，将`numRows`简写为`n`）(k >= 0)
- 第n - 1行：字符的下标为`(n - 1) + (2n - 2) * k` (k >= 0)
- 第 i 行：字符的下标有两种：`(2n - 2) * k + i`和`(2n - 2) * (k + 1) - i` (k >= 0)

题解：

```java
class Solution {
    public String convert(String s, int numRows) {
        if (s.length() < 2 || numRows == 1 || numRows > s.length()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int cycleLen = 2 * numRows - 2;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; i + j < s.length(); j += cycleLen) {
                sb.append(s.charAt(i + j));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < s.length()) {
                    sb.append(s.charAt(j + cycleLen - i));
                }
            }
        }
        return sb.toString();
    }
}
```

- 时间复杂度：Θ(n)
- 空间复杂度：Θ(n)

## 法二：将字符直接放到对应的行里

从左到右遍历字符串，可以发现，字符在Z字形排列时所在行号从`0`变化到`numRows - 1`，又从`numRows - 1` 变到`0`

例：对"`PAYPALISHIRING`" ，`numRows = 5` 

|      | P    | A    | Y    | P    | A    | L    | I    | S    | H    | I    | R    | I    | N    | G    |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 下标 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| 行号 | 0    | 1    | 2    | 3    | 4    | 3    | 2    | 1    | 0    | 1    | 2    | 3    | 4    | 3    |

- rows[0] = PH
- rows[1] = ASI
- rows[2] = YIR
- rows[3] = PLIG
- rows[4] = AN

合起来：res = rows[0] + rows[1] + ... + rows[4] = PHASIYIRPLIGAN

题解：

```java
class Solution {
    public String convert(String s, int numRows) {
        if (s.length() < 2 || numRows == 1 || numRows > s.length()) {
            return s;
        }
        // 初始化
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        // 从左到右遍历字符串，并将字符放到对应的行里
        int currRow = 0, increment = -1;
        for (int i = 0; i < s.length(); i++) {
            rows[currRow].append(s.charAt(i));
            if (currRow == numRows - 1 || currRow == 0) {
                increment *= -1;
            }
            currRow += increment;
        }
        // 按顺序合并各行的字符串
        StringBuilder res = new StringBuilder();
        for (StringBuilder row: rows) {
            res.append(row);
        }
        return res.toString();
    }
}
```

- 时间复杂度：Θ(n)
- 空间复杂度：Θ(n)

## 反思

法一推公式的过程比较不直观，且在代码实现上很容易出错；法二比较直观，代码实现简单，但是算法效率不如法一（虽然二者的时间复杂度都是Θ(n)）。



# 7. 整数反转

给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。

如果反转后整数超过 32 位的有符号整数的范围 [−2<sup>31</sup>,  2<sup>31 </sup>− 1] ，则返回 0。

注：不允许储存64位整数。



记原数值为`x`，翻转后的数字为`rev`。要翻转`x`，就要不断从`x`中弹出末尾数字`digit`，再将其放入`rev`的末尾。

```java
digit = x % 10; // 弹出x的末尾数字
x /= 10; // 更新x
rev = rev * 10 + digit; // 将digit推入rev末尾
```

在将`digit`推入`rev`前，要先判断这个操作会不会造成数值溢出，条件是：
$$
(Integer.MIN\_VALUE - digit) / 10<= rev <= (Integer.MAX\_VALUE - digit) / 10
$$
<span style="color:red">**注意**</span>：这里不能写成
$$
Integer.MIN\_VALUE - digit <= rev * 10 <= Integer.MAX\_VALUE - digit
$$
因为rev * 10可能会造成数值溢出。

证明：当x>0时

- 若rev < 最大值 / 10，二者之间的差值至少为1，而digit / 10 <1，则digit无论多少都不会造成数值溢出，不等式一定成立
- 若rev == 最大值 / 10，则说明x的位数为10，digit为x的最高位，则digit<=2，也不会造成数值溢出，不等式一定成立
- 若rev > 最大值 / 10，二者之间的差值至少为1，而digit / 10 <1，则digit无论多少都**会**造成数值溢出，不等式一定不成立

所以x > 0 时，不造成数值溢出的条件是：rev < 最大值 / 10

同理，当x < 0 时，不造成数值溢出的条件是：rev > 最小值 / 10

代码：

```java
class Solution {
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            rev = rev * 10 + digit;
        }
        return rev;
    }
}
```

- 时间复杂度：Θ(n)（n为x的位数）

- 空间复杂度：Θ(1)




# 10. 正则表达式匹配

## 法一：递归

每次取字符串s的第一个字符与模式串p的第一个字符匹配。

- 如果p[1] != *：直接比较s[0]和p[0]

- 如果p[1] == *：

- - 如果s[0] == p[0]或p[0]=='.'，则接下来有两种选择

  - - 不要这次匹配：return  match(s, p[2:]）
    - 下一个字符继续匹配当前模式：return match(s[1:], p）

  - 否则：当前字符匹配失败，与模式串的下一个字符进行匹配：return match(s, p[2:])

```java
class Solution {
    public boolean isMatch(String s, String p) {
        return match(s, 0, p, 0);
    }

    private boolean match(String s, int i, String p, int j) {
        if (j == p.length()) {
            return i == s.length();
        }
        boolean firstMatch = (i != s.length()) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            if (firstMatch) {
                return match(s, i + 1, p, j) || match(s, i, p, j + 2);
            }
            return match(s, i, p, j + 2);
        }
        return firstMatch && match(s, i + 1, p, j + 1);
    }
}
```

- 时间复杂度：Θ(min(m, n)) ~ O(nm<sup>3</sup>)

  最糟糕的情况下，'*'前的字母与s中的字母一样，但又必须放弃匹配。

  例如：s="aaaaa", p="a\*a\*a\*a\*aaaaa"，此时，match(s, i, p, j)被执行的次数为：

  （下面为 (m + 1) * (n + 1)的矩阵A，A\[i]\[j]表示`match(s, i, p, j)`被执行的次数）（代码详见q10_timeAnalysis.java)

  ```
  i = 0  1 0 1 0 1 0 1 0 0 0 0 0 
  i = 1  1 0 2 0 3 0 3 1 0 0 0 0 
  i = 2  1 0 3 0 6 0 6 3 1 0 0 0 
  i = 3  1 0 4 0 10 0 10 6 3 1 0 0 
  i = 4  1 0 5 0 15 0 15 10 6 3 1 0 
  i = 5  1 0 6 0 21 0 21 15 10 6 3 1 
  ```

  1 + 3 + 6 + 10 + ... + m(m+1)/2 = m(m + 1)(m + 2) / 6 = O(m<sup>3</sup>)

  大致估计这种情况下的时间复杂度为 O(nm<sup>3</sup>)

- 空间复杂度：Θ(1)

## ※法二：动态规划

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(j - 1) == '*') {
                    if (matches(s, i - 1, p, j - 2)) {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 2];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                } else if (matches(s, i - 1, p, j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    private boolean matches(String s, int i, String p, int j) {
        if (i == -1) {
            return false;
        }
        return s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
    }
}
```

- 时间复杂度：Θ(mn)
- 空间复杂度：Θ(mn)

# 11. 盛最多水的容器

## 双指针法

设置两个指针分别位于容器两壁，即`i = 0`, `j = n - 1`，此时容器的容量为`(j - i) * Math.min(h[i], h[j])`。

假设`h[i] < h[j]`，易知，以`h[i]`为左边界的容器，容量均小于当前容器的容量。（因为容器的短板长度一定<=`h[i]`，而容器宽度不可能变长）

<img src="images/image-20210610155406017.png" alt="image-20210610155406017" style="zoom: 67%;" />

所以，要寻找比当前容量更大的容器，应该向内移动高度较小的边界，即将左边界向右移，或将有边界向左移。

```java
class Solution {
    public int maxArea(int[] height) {
        int res = 0;
        for (int i = 0, j = height.length - 1; i < j; ) {
            // i++ : 先返回 i，再执行 i = i + 1
            int minHeight = height[i] < height[j] ? height[i++] : height[j--];
            int s = (j - i + 1) * minHeight;
            res = Math.max(res, s);
        }
        return res;
    }
}
```

- 时间复杂度：Θ(N)
- 空间复杂度：Θ(1)

# 15. 三数之和

## 双指针法

- 为了避免结果中有**重复**的三元组(a, b, c) ，首先要将`nums`按由小到大排序，并且避免元素的重复出现。
- 然后，可以用**三重循环**列举处所有可能情况，但是这样会造成TLE，因此要对三重循环进行优化。
- 已知第一个元素为a，则后两个元素的和一定为-a，此时可将问题转化为求解**两数之和**。由于该数组已经排序好了，可以用**双指针法**在Θ(N)内解决问题（具体分析请看[这里](https://leetcode-cn.com/problems/3sum/solution/san-shu-zhi-he-by-leetcode-solution/)）。如此，便将后两重循环的时间复杂度从Θ(N<sup>2</sup>)降到Θ(N)。

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        // 枚举第一个元素
        for (int i = 0; i < nums.length; i++) {
            // 避免第一个元素重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i], k = nums.length - 1;
            // 枚举第二个元素
            for (int j = i + 1; j < nums.length; j++) {
                // 避免第二个元素重复
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                while (j < k && nums[j] + nums[k] > target) {
                    k -= 1;
                }
                if (j == k) {
                    break;
                }
                if (nums[j] + nums[k] == target) {
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));
                }
            }
        }
        return res;
    }
}
```

- 时间复杂度：Θ(N<sup>2</sup>)
- 空间复杂度：Θ(logN)。java的sort()函数用了快速排序和归并排序。快排需要的额外的空间为Θ(logN)（递归栈），归并排序需要的额外的空间为Θ(N) + Θ(logN)（递归栈）。



# 17. 电话号码的字母组合

## 回溯法

看到这道题我立马想到用多重循环，但是由于`digits`的个数不定，所以要实现一个**不定重数的多重循环**，这就要用**递归**辅助实现了。[LeetCode官方题解](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/solution/dian-hua-hao-ma-de-zi-mu-zu-he-by-leetcode-solutio/)和我一样，不过他们把这称为**“回溯法**”，想来确实没错。所以回溯法本质是一种更智能的多重循环吧，这里的“智能”是指可以剪枝、重数不定等。

<img src="images/image-20210612155757935.png" alt="17.电话号码的字母组合 图解" style="zoom:67%;" />

```java
class Solution {
    String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return res;
        }
        recursion(digits, 0, new StringBuilder());
        return res;
    }

    private void recursion(String digits, int i, StringBuilder cur) {
        if (i == digits.length()) {
            res.add(cur.toString());
            return;
        }
        String letters = map[Character.getNumericValue(digits.charAt(i))];
        for (int j = 0; j < letters.length(); j++) {
            cur.append(letters.charAt(j));
            recursion(digits, i + 1, cur);
            cur.deleteCharAt(i);
        }
    }
}
```

- 时间复杂度：O(3<sup>m</sup> 4<sup>n</sup>)，其中n为数字7和9在输入中出现的次数，m为其它数字出现的次数。
- 空间复杂度：Θ(m + n)（递归调用层数）

# 78. 子集

给你一个整数数组 `nums` ，数组中的元素 **互不相同** 。返回该数组所有可能的子集（幂集）。

## 法一：二进制表示

记原序列的元素总数为n。每个元素有出现（记为1）和不出现（记为0）两种状态，则子集一共有 2<sup>n </sup>种。

以{1, 2, 3}为例，用**0/1序列**表示出现的元素，序列的倒数第i位对应元素的正数第i位，例如，序列100对应子集{3}。

| 0/1序列 | 对应子集    | 对应二进制数 |
| ------- | ----------- | ------------ |
| 000     | {} （空集） | 0            |
| 001     | {1}         | 1            |
| 010     | {2}         | 2            |
| 011     | {1, 2}      | 3            |
| 100     | {3}         | 4            |
| 101     | {1, 3}      | 5            |
| 110     | {2, 3}      | 6            |
| 111     | {1, 2, 3}   | 7            |

可以发现，0/1序列正好是数字0\~2<sup>n</sup>-1的二进制表示，所以我们可以枚举0\~2<sup>n</sup>-1的二进制数字，然后将其转换成对应的子集。

代码：

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = 1 << nums.length;
        for (int i = 0; i < n; i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0, index = i; j < nums.length && index != 0; j++, index >>= 1) {
                if ((index & 1) == 1) {
                    subset.add(nums[j]);
                }
            }
            res.add(subset);
        }
        return res;
    }
}
```

- 时间复杂度：Θ(n * 2<sup>n</sup>) （ 共2<sup>n </sup>种子集，每个子集需要n个操作）

- 空间复杂度：Θ(n)（临时数组所需空间）

  

**笔记：**

- 1 << n = 100...00 （共n个0）= 2 <sup>n</sup>
- n >> 1：将二进制数的各位全部右移1位。例如，1111 >> 1 后变为 0111
- n & 1可判断n的奇偶性
  - n & 1 == 1，则n为奇数
  - n & 1 == 0，则n为偶数



## 法二：DFS

每个元素有“选”与“不选”两种状态，假设“不选”进入左子树，“选”进入右子树，第i层代表第i个元素的选择情况，可以画出一棵完全二叉树，它的一个叶结点表示一种子集。用DFS遍历该二叉树，即可得到所有子集。

<img src="E:\1-CS\0-刷题\Coding4Interviews\hot100\images\596a16391607ec3505794d6ecb4d5d854f480440f025fc646c9f6225f83412e0-image.png" alt="image.png" style="zoom:60%;" />

代码：

```java
class Solution {
    public List<List<Integer>> res = new ArrayList<>();
    public List<Integer> subset = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        dfs(0, nums);
        return res;
    }

    public void dfs(int i, int[] nums) {
        if (i == nums.length) {
            res.add(new ArrayList(subset));
            return;
        }
        subset.add(nums[i]);
        dfs(i + 1, nums);
        subset.remove(subset.size() - 1);
        dfs(i + 1, nums);
    }
}
```



## 法三：回溯法

每个元素不再有“选”与“不选”两种状态，而是考虑它被选之后还有什么元素可选。对每一个元素，它的下一个元素的可选范围应该为原数组内位于它之后的元素；

<img src="E:\1-CS\0-刷题\Coding4Interviews\hot100\images\d8e07f0c876d9175df9f679fcb92505d20a81f09b1cb559afc59a20044cc3e8c-子集问题递归树.png" alt="子集问题递归树.png" style="zoom:70%;" />

```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> subset = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtrack(0, nums);
        return res;
    }

    public void backtrack(int i, int[] nums) {
        res.add(new ArrayList(subset));
        for (int j = i; j < nums.length; j++) {
            subset.add(nums[j]);
            backtrack(j + 1, nums);
            subset.remove(subset.size() - 1);
        }
    }
}
```

==对比：下面的代码为什么报错？==

```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> subset = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtrack(nums, 0);
        return res;
    }

    private void backtrack(int[] nums, int i) {
        res.add(new ArrayList(subset));
        subset.add(nums[i]);
        for (int j = i + 1; j < nums.length; j++) {
            backtrack(nums, j);
            subset.remove(subset.size() - 1);
        }
    }
}
```

答：回溯法末端的子集没有放入res中