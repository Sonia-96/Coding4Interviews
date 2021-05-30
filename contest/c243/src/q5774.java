import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.Assert.assertArrayEquals;

public class q5774 {
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

    @Test
    public void test() {
        assertArrayEquals(new int[] {2,2,0,2,1,2}, assignTasks(new int[]{3, 3, 2}, new int[]{1, 2, 3, 2, 1, 2}));
        assertArrayEquals(new int[] {1,4,1,4,1,3,2}, assignTasks(new int[] {5,1,4,3,2}, new int[] {2,1,2,4,5,2,1}));
        assertArrayEquals(new int[] {1, 0, 1}, assignTasks(new int[] {4, 2}, new int[] {4, 3, 2}));
        // 超时测试
        int[] b = new int[200000];
        for (int i = 0; i < 100000; i++) {
            b[i] = 100000;
        }
        for (int i = 100000; i < 200000; i++) {
            b[i] = 1;
        }
        int[] a = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assignTasks(a, b);
    }
}
