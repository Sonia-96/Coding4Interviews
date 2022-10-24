[TOC]

| 日期       | #周赛 | 题目                                                         | 解决数 | 排名                 |
| ---------- | ----- | ------------------------------------------------------------ | ------ | -------------------- |
| 2021-05-16 | 241   | [1863. 找出所有子集的异或总和再求和](#1863. 找出所有子集的异或总和再求和)<br>[1864. 构成交替字符串需要的最小交换次数](#1864. 构成交替字符串需要的最小交换次数)<br>1865. 找出和为指定值的下标对<br>1866. 恰有 K 根木棍可以看到的排列数目 | 1/4    | 2627 / 4490（58.5%） |
| 2021-05-23 | 242   | 1869. 哪种连续字符串更长<br>1870. 准时到达的列车最小时速<br>[1871. 跳跃游戏VII](#1871. 跳跃游戏VII) | 1/4    | 27096/53593（50.5%） |
| 2021-05-30 | 243   | [5772. 检查某单词是否等于两单词之和](#5772. 检查某单词是否等于两单词之和)<br>[5773. 插入后的最大值](#5773. 插入后的最大值)<br>==[5774. 使用服务器处理任务](#5774. 使用服务器处理任务)== | 2/4    | 1593 / 4492（35.5%） |
| 2021-06-13 | 245   |                                                              | 1/4    | 2234/4270（54.7%）   |
| 2021-06-20 | 246   |                                                              | 2/4    | 1700 / 4135（41.1%） |



# 周赛#241

## 1863. 找出所有子集的异或总和再求和

异或（^）位操作：值不同为1，相同为0

- 满足交换律和结合律

- x ^ 0 = x, x ^ x = 1
- a ^ b ^ b = a ^ 0 = a

### 法一：二进制模拟

```java
class Solution {
    public int subsetXORSum(int[] nums) {
        int n = 1 << nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int subset = 0;
            for (int idx = i, j = 0; idx > 0; idx >>= 1, j++) {
                if ((idx & 1) == 1) {
                    subset ^= nums[j];
                }
            }
            res += subset;
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
class Solution {
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



# 周赛#242

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
  - 上界：maxV = 10<sup>5</sup>/0.01 = 10<sup>7</sup>（因为dist[i] <=10<sup>5</sup>；hour至多两位小数，所以时间最小值为0.01h）
  - 上下界可继续缩小，不过影响不大
- **<u>计算中位数</u>**：midV = minV + (maxV - minV) / 2 （这种写法可以避免minV + maxV造成的数值溢出）
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
            int midV = minV + (maxV - minV) / 2;
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



## 1871. 跳跃游戏VII

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

- 从下标`i`起跳，则可到达的区域为：[i + minJump, i + maxJump]中字符'0'的下标。记到达的位置为j。
- 又从`j`起跳，可达到的区域为： [j + minJump, j + maxJump]中字符'0'的下标
- ……
- 重复以上过程，如果访问到位置`s.length() - 1`且该位置为'0'，则返回true；如果该位置为1或不能到达该位置，则返回false。



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
            if (curr == s.length() - 1) {
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



**反思**：比赛时我的方法类似法二，但是当时没有意识到这是BFS，采用了递归的实现方法，得到了TLE，但是没有找到超时的原因，故最终没有通过。看来我对算法的掌握还是不够熟练。



# 周赛#243

## 5772. 检查某单词是否等于两单词之和

```java
class Solution {
    public boolean isSumEqual(String firstWord, String secondWord, String targetWord) {
        return wordSum(firstWord) + wordSum(secondWord) == wordSum(targetWord);
    }

    private int wordSum(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 10 + s.charAt(i) - 'a';
        }
        return res;
    }
}
```



## 5773. 插入后的最大值

- 如果n是正数：找到第一个比x小的数，然后将x放到它前面。如果没有则将x放至末尾
  - 例如：n=763，x=5 → 7653
- 如果n是负数：找到第一个比x大的数，然后将x放到它前面。如果没有则将x放至末尾
  - 例如：n=-13，x=2 → -123

代码：

```java
class Solution {
    public String maxValue(String n, int x) {
        int i;
        if (n.charAt(0) == '-') {
            i = 1;
            while (i < n.length() && Character.getNumericValue(n.charAt(i)) <= x) {
                i+= 1;
            }
        } else {
            i = 0;
            while (i < n.length() && Character.getNumericValue(n.charAt(i)) >= x) {
                i+= 1;
            }
        }
        return n.substring(0, i) + Character.forDigit(x, 10) + n.substring(i);
    }
}
```



## 5774. 使用服务器处理任务

- 使用两个最小堆储存服务器，堆顶的服务器优先级最高：
  - `idle`：储存空闲的服务器，储存格式为[w, idx] （w为服务器权重，idx为服务器下标）
  - `busy`：储存工作中的服务器，储存格式为[t, idx]（t为任务完成时间）
- 初始时，将所有服务器存到`idle`里
- 用指针`i`遍历每一个任务，用`clock`表示当前时间
  - 由于任务`tasks[i]`只能在第`i`秒之后被处理，所以设置`clock = min(i, clock)`
  - 将`busy`中`t<=clock`的服务器（即已完成工作的服务器）放到`idle`里
  - 如果当前`idle`为空，则将`clock`调至下一个任务完成的时间，然后重复上一步骤
  - 如果当前`idle`不为空，则弹出`idle`堆顶的服务器，用来处理`tasks[i]`（即将该服务器放入`busy`）
- 注意：千万不要受题目的诱导而设置`clock`的步长为1，这样可能会超时！！（比如，只有1个服务器，却有10<sup>5</sup>个任务，每个任务的处理时间为10<sup>5</sup>s，则一共需要循环10<sup>10</sup>次，妥妥超时！）👈这就是我使用了双堆法却仍然TLE的原因🙃

代码：

```java
class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        // 空闲服务器：[weight, serverIndex]
        PriorityQueue<int[]> idle = new PriorityQueue<>(new serverComparator());
        for (int i = 0; i < servers.length; i++) {
            idle.add(new int[] {servers[i], i});
        }
        // 工作中的服务器：[finishTime, serverIndex]
        PriorityQueue<int[]> busy = new PriorityQueue<>(new serverComparator());
        int[] ans = new int[tasks.length];
        int clock = 0, i = 0;
        while (i < tasks.length) {
            clock = Math.max(i, clock); // tasks[i]要在第i秒后才能开始处理
            // 将完工的服务器加入idle中
            while (!busy.isEmpty() && busy.peek()[0] <= clock) {
                int idx = busy.poll()[1];
                idle.add(new int[] {servers[idx], idx});
            }
            // 如果没有空闲的服务器，则将时间调至下一个服务器空闲的时刻
            if (idle.isEmpty()) {
                clock = busy.peek()[0];
            } else { // 如果有空闲服务器，则取出队首服务器处理当前任务
                int idx = idle.poll()[1];
                ans[i] = idx;
                busy.add(new int[] {clock + tasks[i], idx});
                i += 1;
            }
        }
        return ans;
    }

    private static class serverComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] o1, int[] o2) {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        }
    }
}
```



# 周赛#245

## 1898. 可移除字符的最大数目

### 二分查找右边界+子序列判断

- 观察：如果移除`removable`的前k+1个元素后，`p`仍然是`s`的子序列，那么，只移除前k个元素，`p`也一定是`s`的子序列。
- 使用二分查找可找到k的最大值，时间复杂度Θ(logN)
- 对每一个k值，都要判断`p`是否为`s`的子序列，时间复杂度Θ(N)【参考leetcode392.判断子序列】
- 总的时间复杂度：Θ(NlogN)

```java
class Solution {
    public int maximumRemovals(String s, String p, int[] removable) {
        int left = 0, right = removable.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (check(s, p, removable, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left - 1;
    }

    private boolean isSubsequence(String s, String p, int[] removable, int k) {
        boolean[] removed = new boolean[s.length()];
        for (int i = 0; i < k; i++) {
            removed[removable[i]] = true;
        }
        int i = 0, j = 0;
        while (i < s.length() && j < p.length()) {
            if (!removed[i] && s.charAt(i) == p.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == p.length();
    }
}
```

### 总结：二分查找的两种写法

左闭右开是指变量的取值范围为[left, right)，左闭右闭是指变量的取值范围为[left, right]

|          | left初始值 | right初始值 | 循环条件      | left更新 | right更新 |
| -------- | ---------- | ----------- | ------------- | -------- | --------- |
| 左闭右闭 | 0          | n - 1       | left <= right | left + 1 | right - 1 |
| 左闭右开 | 0          | n           | left < right  | left + 1 | right     |

在这一题中，我采用的是“左闭右闭”，另外，因为查找的是右边界，所以最后return left - 1。

# 周赛#246

## 1903. 字符串中最大的奇数

本题采用贪心策略解答。

- 数字的奇偶性只与其末位数字有关。另外，位数越多，数字越大，因此，最后返回的字符串起始下标一定是0。
- 要使位数最多，就要使字符串最长，因此，找到字符串中**最靠右**的奇数，记其下标为end，则字符串中的最大奇数为s[0...end]。

```java
class Solution {
    public String largestOddNumber(String num) {
        int end = 0;
        // 从右到左遍历，找到第一个奇数即可返回
        for (int i = num.length() - 1; i >= 0 ; i--) {
            if ((num.charAt(i) & 1) == 1) {
                end = i + 1;
                break;
            }
        }
        return num.substring(0, end);
    }
}
```

知识点：0~1的字符的ASCII码值与对应的数字奇偶性相同。可用下面的代码验证：

```java
for (int i = 0; i < 10; i++) {
    int tmp = (int) Character.forDigit(i, 10);
    if ((tmp & 1) != (i & 1)) {
        System.out.println("0~9的字符与其ASCII码值奇偶性不一定相同！");
        break;
    }
}
System.out.println("0~9的字符与其ASCII码值奇偶性相同！");
```



## 1904. 你完成的完整对局数

比赛的时候做出来了，但是代码很复杂，下面这个是改进版。

```java
class Solution {
    public int numberOfRounds(String startTime, String finishTime) {
        int start = toMinutes(startTime);
        int finish = toMinutes(finishTime);
        finish += finish < start ? 1440 : 0;
        finish = finish / 15 * 15; // 第一个<=finishTime的完整对局结束的时间
        return (finish - start) / 15;
    }

    private int toMinutes(String time) {
        int h = Integer.parseInt(time.substring(0, 2));
        int min = Integer.parseInt(time.substring(3, 5));
        return h * 60 + min;
    }
}
```



## 1905. 统计子岛屿

- 判断岛屿：DFS / BFS
- 判断子岛屿：grid2中的岛屿的**每一个**格子在grid1中均为1【就是这里没有想到！！】

### 法一：DFS

```java
class Solution {
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    if(dfs(grid1, grid2, i, j)) {
                        count += 1;
                    }
                }
            }
        }
        return count;
    }

    private boolean dfs(int[][] grid1, int[][] grid2, int i, int j) {
        int m = grid1.length, n = grid1[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n || grid2[i][j] == 0) {
            return true;
        }
        grid2[i][j] = 0;
        boolean up = dfs(grid1, grid2, i - 1, j);
        boolean down = dfs(grid1, grid2, i + 1, j);
        boolean left = dfs(grid1, grid2, i, j - 1);
        boolean right = dfs(grid1, grid2, i, j + 1);
        return grid1[i][j] == 1 && (up & down & left & right);
    }
}
```

- 在判断语句中，`&&` 和 `&`的区别
  - `&`左右两侧的式子都必须运算
  - `&&`具有短路的功能，如果其左侧的式子返回`false`，则不再对右侧的式子进行运算。所以`&&`的效率更高

### 法二：BFS

```java
class Solution {
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    count += bfs(grid1, grid2, i , j) ? 1 : 0;
                }
            }
        }
        return count;
    }

    private boolean bfs(int[][] grid1, int[][] grid2, int i, int j) {
        grid2[i][j] = 0;
        int m = grid1.length, n = grid1[0].length;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(i * n + j);
        boolean check = grid1[i][j] == 1;
        while (!queue.isEmpty()) {
            int p = queue.remove();
            int row = p / n, col = p % n;
            if (row - 1 >= 0 && grid2[row - 1][col] == 1) {
                grid2[row - 1][col] = 0;
                queue.add((row - 1) * n + col);
                check &= grid1[row - 1][col] == 1;
            }
            if (row + 1 < m && grid2[row + 1][col] == 1) {
                grid2[row + 1][col] = 0;
                queue.add((row + 1) * n + col);
                check &= grid1[row + 1][col] == 1;
            }
            if (col - 1 >= 0 && grid2[row][col - 1] == 1) {
                grid2[row][col - 1] = 0;
                queue.add(row * n + col - 1);
                check &= grid1[row][col - 1] == 1;
            }
            if (col + 1 < n && grid2[row][col + 1] == 1) {
                grid2[row][col + 1] = 0;
                queue.add(row * n + col + 1);
                check &= grid1[row][col + 1] == 1;
            }
        }
        return check;
    }
}
```

注意：区分两种更新check的方式：

- 法一：check = gird1\[i]\[j] == 1
- 法二：check = check & gird1\[i]\[j] == 1

法一的check值只由岛屿中最后一个格子决定；而法二中，只要岛屿中任一格子对应的grid1不为1，check就为false。所以法二是正确的。

# Week Contest #316

## 2446. Determine if Two Events Have Conflict

You are given two arrays of strings that represent two inclusive events that happened **on the same day**, `event1` and `event2`, where:

- `event1 = [startTime1, endTime1]` and
- `event2 = [startTime2, endTime2]`.

Event times are valid 24 hours format in the form of `HH:MM`.

A **conflict** happens when two events have some non-empty intersection (i.e., some moment is common to both events).

Return `true` *if there is a conflict between two events. Otherwise, return* `false`.

**Example 1:**

```
Input: event1 = ["01:15","02:00"], event2 = ["02:00","03:00"]
Output: true
Explanation: The two events intersect at time 2:00.
```

**Example 2:**

```
Input: event1 = ["01:00","02:00"], event2 = ["01:20","03:00"]
Output: true
Explanation: The two events intersect starting from 01:20 to 02:00.
```

**Example 3:**

```
Input: event1 = ["10:00","11:00"], event2 = ["14:00","15:00"]
Output: false
Explanation: The two events do not intersect.
```

 ### Approach #1: no overlap

This question is to determine if two line segments have overlapping parts. If they don't have overlaps, they meet the following condition: `end1 < start2 || end2 < start1 `. Overlap and no-overlap are exclusive, so we should return `!(end1 < start2 || end2 < start1)`. 

Note, we can use `.compareTo()` to compare two time directly, instead of writing our own comparator.

```java
class Solution {
    public boolean haveConflict(String[] event1, String[] event2) {
        return !(event1[1].compareTo(event2[0]) < 0 || event2[1].compareTo(event1[0]) < 0);
    }
}
```

### Approach #2: overlap

If two events have conflict, then `Math.max(start1, start2) <= Math.min(end1, end2)`, so `start1 <= end2 && start2 <= end1`.

```java
class Solution {
    public boolean haveConflict(String[] event1, String[] event2) {
        return event1[0].compareTo(event2[1]) <= 0 && event2[0].compareTo(event1[1]) <= 0;
    }
}
```

## 2447. Number of Subarrays With GCD Equal to K

```java
class Solution {
  private int GCD(int a, int b) {
        while (b > 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    public int subarrayGCD(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) { // start index
            int gcd = nums[i];
            if (gcd == k) {
                count += 1;
            }
            for (int j = i + 1; j < nums.length; j++) { // end index
                gcd = GCD(gcd, nums[j]);
                if (gcd < k) {
                    break;
                }
                if (gcd == k) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
```

Complexity Analysis:

- Time Complexity: O(n<sup>2</sup>logn)
  - iterate over all subarrays: O(n<sup>2</sup>)
  - time complexity for gcd(a, b) is O(log(ab)) = O(loga + logb) = O(logo)
- Space Complexity: O(1)



From this question, I learn:

1. how to iterate the subarrays of an array:

   ```java
   for (int i = 0; i < nums.length; i++) { // start index
     for (int j = i; j < nums.length; j++) { // end index
       
     }
   }
   ```

   

2. how to calculate the GCD of two numbers: GCD(a, b) = GCD(b, a % b) = ... = GCD(b', 0) = b'

   ```java
   GCD_iter(int a, int b) {
     while (a > 0) {
       int remainder = a % b;
       a = b;
       b = remainder;
     }
     return a;
   }
   
   GCD_recur(int a, int b) {
     if (b == 0) return a;
    	return GCD_recur(b, a % b);
   }
   ```

   
