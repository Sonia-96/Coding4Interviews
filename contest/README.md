[TOC]

| 日期       | #周赛 | 题目                                                         | 解决数 | 排名        |
| ---------- | ----- | ------------------------------------------------------------ | ------ | ----------- |
| 2021-05-16 | 241   | [1863. 找出所有子集的异或总和再求和](#1863. 找出所有子集的异或总和再求和)<br>[1864. 构成交替字符串需要的最小交换次数](#1864. 构成交替字符串需要的最小交换次数)<br>1865. 找出和为指定值的下标对<br>1866. 恰有 K 根木棍可以看到的排列数目 | 1/4    | 2627 / 4490 |
| 2021-05-23 | 242   | 1869. 哪种连续字符串更长<br>1870. 准时到达的列车最小时速<br>[1871. 跳跃游戏VII](#1871. 跳跃游戏VII) | 1/4    | 27096/53593 |
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



# 周赛242

## 1869. 哪种连续子字符串更长

```java
public class Solution {
        public boolean checkZeroOnes(String s) {
        int len1 = consecutiveLength(s, '1');
        int len0 = consecutiveLength(s, '0');
        return len1 > len0;
    }

    public int consecutiveLength(String s, char c) {
        int maxLen = 0, cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                cnt += 1;
            } else {
                maxLen = Math.max(maxLen, cnt);
                cnt = 0;
            }
        }
        maxLen = Math.max(maxLen, cnt); // 对字符串末尾的连续子串
        return maxLen;
    }
}
```



## 1870. 准时到达的列车最小时速

使用二分法搜索。

- 速度的上下界：
  - 下界：minV = 1
  - 上界：maxV = 10<sup>5</sup>/0.01 = 10<sup>7</sup>（因为dist[i] <=10<sup>5</sup>，hour至多两位小数，所以时间最小值为0.01h）
  - （上下界可继续缩小，不过影响不大）
- **<u>计算中位数</u>**：midV = minV + (maxV - minV) / 2 （这种写法可以避免minV + maxV造成数值溢出）
- 计算midV到达终点所需时间time
  - 对dist[0] ~ dist[n - 2]：由于列车整点发车，所以时间必须取整。<u>**取整有两种方法**</u>:
    - (dist[i] - 1) / midV + 1
    - Math.ceil(dist[i] / midV)
  - dist[n - 1]不用取整
- 二分搜索：while minV < maxV：
  - 如果time <= hour：可以按时到达，但此时的midV未必是最小值，要继续向下寻找，则maxV=midV
  - 如果time > hour：说明midV小了，要向上寻找，则minV=midV + 1



本题难点：

- 界定速度的上下界
- 浮点数的比较（误差多少算相等？）
- 二分搜索中[minV, maxV] 的变化（midV加不加一很重要）



代码：

```java
public class Solution {
    public int minSpeedOnTime(int[] dist, double hour) {
        if (dist.length - 1 >= hour) {
            return -1;
        }
        int minV = 1;
        int maxV = 10000000;
        while (minV < maxV) {
            int midV = (minV + maxV) / 2;
            double time = getTime(dist, midV);
            if (time <= hour) {
                maxV = midV;
            } else {
                minV = midV + 1;
            }
        }
        return minV;
    }

    public double getTime(int[] dist, int midV) {
        double t = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            t += Math.ceil((dist[i] + 0.0)/ midV);
        }
        t += (dist[dist.length - 1] + 0.0) / midV;
        return t;
    }
}
```

- 时间复杂度：Θ(NlogN)
- 空间复杂度：Θ(1)



反思：比赛时想到从最小速度往上逐个暴力搜索，结果毫不意外地TLE了。当时没想到速度的上界（其实现在想来可以试试用`Integer.MAX_VALUE`的），如果想到一定会使用二分搜索的。就差一点点了呀，下次比赛的时候要再多思考下！



## [1871. 跳跃游戏VII](https://leetcode-cn.com/problems/jump-game-vii/)

### 法一：动态规划

令`f[i]`表示能否从位置0跳到位置`i`，则`f(i) = true`的条件为：

- `s[i] = '0'`
- 假设最后一步是从位置`j`跳到位置`i`的，则
  -  `j`的取值为：`i - maxJump <= j <= i - minJump`且`j`>=0
  - 对以上`j`的取值，至少有一个`f[j]`为`true`

因此，本题的状态转移方程为：
$$
f(i) = any(f(j))，其中 j∈[i−maxJump,i−minJump] 且 j≥0
$$



记`left = i - maxJump`，`right = i - minJump`，则j的取值范围为[left, right]。那么，如何检验其中是否有`f[j]`为`true`？

- 初级：遍历f[left] ~ f[right]，检查是否有f[j]为true
- 优化：用一个值`total`记录f[left] ~ f[right]的和，若`total>0`则至少有一个`f[j]`为`true`，则f[i]=true

于是，状态转移方程可优化为：
$$
f(i) =(\sum_{j=left}^{right}f(j) > 0) =( total > 0 )
$$



那么，如何计算`total`？

- 初级：设置前缀数组`pre`，`pre[i]`表示`dp[0...i]`的和，则f[i]的total = pre[right] - pre[left - 1]
- 优化：**滑窗法**
  - 由于[left, right]区间长度固定，`i`每增加1，区间就向右滑动一格，因此只需用一个值记录窗口内的和，无需使用前缀数组
  - 注意：当 i 处于 `[minJump, maxJump)` 时，滑窗尚不完整，需特殊处理

代码：

```java
public class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        boolean[] dp = new boolean[s.length()];
        dp[0] = true;
        int total = 1;
        for (int i = minJump; i < s.length(); i++) {
            int left = i - maxJump, right = i - minJump;
            if (i > minJump) { // i > minJump时，窗口右端才开始滑动
                total += dp[right] ? 1 : 0;
            }
            if (left > 0) { // left > 0时，窗口左端才开始滑动
                total -= dp[left - 1] ? 1 : 0;
            }
            dp[i] = s.charAt(i) == '0' && total >0;
        }
        return dp[s.length() - 1];
    }
}
```

- 时间复杂度：Θ(N)
- 空间复杂度：Θ(N)



总结：动态规划的优化思路

- 首先推出状态转移方程：f(i) = any(f(j))

- 判断对j∈[left, right]是否有f(j)为true：
  - 初级：从左到右遍历，时间复杂度为Θ(M)，其中M=right - left + 1
  - 进阶：想到f(i)=(Σf(j)> 0)，使用**前缀数组**可以将这一步的时间复杂度降至Θ(1)，但内存使用增加Θ(N)
  - 最优：由于[left, right]区间长度固定，可使用**滑窗法**取代前缀数组，使内存进一步优化



### 法二：BFS

模拟跳跃游戏的过程：

- 从下标`i`起跳，则可到达的区域为：[i + minJump, i + maxJump]中为字符为'0'的下标。记到达的位置为j。
- 又从`j`起跳，可达到的区域为： [j + minJump, j + maxJump]中字符为'0'的下标
- ……
- 重复以上过程，如果访问到位置`s.length() - 1`且该位置为0，则返回true；如果该位置为1或不能到达该位置，则返回false。



代码实现：BFS

- 用队列储存待访问的位置。首先放入下标0
- 弹出并访问位置0，并放入下一步能到达的下标（即[left, right]中字符0的下标）
- 弹出队首结点并访问，并放入下一步能达到的下标
- 重复上一步直至队列为空

在以上方法中，结点会被重复访问（假设minJump=1，maxJump=2，则0和1被访问1次，2被访问2次，3被访问3次，n被访问n次……），算法的时间复杂度为Θ(n<sup>2</sup>)，从而导致超时。

为了避免重复访问，用指针`last`记录最后访问的位置，每次从[max(last + 1, left), right]中往队列内放入下一步能到达的下标。

代码：

```java
public class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        int last = 0;
        while (!queue.isEmpty()) {
            int curr = queue.remove();
            if (curr == s.length() - 1 && s.charAt(curr) == '0') {
                return true;
            }
            int left = curr + minJump, right = Math.min(curr + maxJump, s.length() - 1);
            for (int i = Math.max(left, last + 1); i <= right; i++) {
                if (s.charAt(i) == '0') {
                    queue.add(i);
                }
            }
            last = right;
        }
        return false;
    }
}
```

- 时间复杂度：Θ(N)
- 空间复杂度：Θ(N)



### 反思

比赛的时候我的方法类似法二，但是当时没有意识到这是BFS，采用了递归的实现方法，得到了TLE，但是没有找到超时的原因，故最终没有通过。看来我对算法的掌握还是不够熟练。



