[TOC]

| 日期       | #周赛 | 题目                                                         | 解决数 | 排名        |
| ---------- | ----- | ------------------------------------------------------------ | ------ | ----------- |
| 2021-05-16 | 241   | [1863. 找出所有子集的异或总和再求和](#1863. 找出所有子集的异或总和再求和)<br>[1864. 构成交替字符串需要的最小交换次数](#1864. 构成交替字符串需要的最小交换次数)<br>1865. 找出和为指定值的下标对<br>1866. 恰有 K 根木棍可以看到的排列数目 | 1/4    | 2627 / 4490 |
|            |       |                                                              |        |             |
|            |       |                                                              |        |             |



# 周赛#241

## 1863. 找出所有子集的异或总和再求和

### 法一：二进制模拟

```java
public Solution {
    public int subsetXORSum(int[] nums) {
        int res = 0;
        int n = 1 << nums.length;
        for (int i = 0; i < n; i++) {
            int curr = 0;
            int index = i;
            for (int j = 0; j < nums.length; j++) {
                if ((index & 1) == 1) {
                    curr = curr ^ nums[j];
                }
                index = index >> 1;
            }
            res += curr;
        }
        return res;
    }
}
```



### 法二：DFS

```java
public class Solution {
    public int total = 0;

    public int subsetXORSum(int[] nums) {
        dfs(0, nums, 0);
        return total;
    }

    public void dfs(int i, int[] nums, int curr) {
        if (i == nums.length) {
            total += curr;
            return;
        }
        dfs(i + 1, nums, curr ^ nums[i]); // 选择该元素
        dfs(i + 1, nums, curr); // 不选该元素
    }
}
```



### ※法三：回溯法

```java
public Solution {
    public int total = 0;

    public int subsetXORSum(int[] nums) {
        backtrack(0, nums, 0);
        return total;
    }
    
    public void backtrack(int i, int[] nums, int curr) {
        total += curr;
        for (int j = i; j < nums.length; j++) {
            backtrack(j + 1, nums, curr ^ nums[j]);
        }
    }
}
```





## 1864. 构成交替字符串需要的最小交换次数

策略：

- 计算字符串种0和1出现的个数 `num0` 和 `num1`
  - 如果|num0 - num1| > 1，则该字符串无法通过交换字符得到交替字符串
- 目标字符串形式：
  - 如果 num0 == num1，则目标字符串有两种可能性：
    - 0101...01（1都位于奇数位）
    - 1010..10（1都位于偶数位）
  - 如果 num0 > num1， 则目标字符串只能为 0101..0（1都位于奇数位）
  - 如果 num0 < num1，则目标字符串只能为 1010..1（1都位于偶数位）
- 计算交换次数：
  - 遍历字符串，记录位于奇数位的1的个数，记作 `odd1`
  - 如果 num0 > num1：1都应该位于奇数位，所以要把偶数位上的1与奇数位上的0交换，即返回`num1 - odd1`
  - 如果 num0 < num1：1都应该位于偶数位，所以要把奇数位上的1与偶数位上的0交换，即返回`odd1`
  - 如果 num0 == num1：返回`Math.min(odd1, num1 - odd1)`



代码：

```java
class Solution {
    public int minSwaps(String s) {
        int num0 = 0, num1 = 0, odd1 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                num0 += 1;
            } else {
                num1 += 1;
                if ((i & 1) == 1) {
                    odd1 += 1;
                }
            }
        }
        if (Math.abs(num0 - num1) > 1) {
            return -1;
        }
        if (num0 == num1) {
            return Math.min(num1 - odd1, odd1);
        } else if (num0 > num1) { // 目标字符串中1全在奇数位
            return num1 - odd1;
        } else { // 目标字符串中1全在偶数位
            return odd1;
        }
    }
}
```

