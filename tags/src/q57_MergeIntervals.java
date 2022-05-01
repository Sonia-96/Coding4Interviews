import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class q57_MergeIntervals {

    // for loop
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

    @Test
    public void test1() {
        int[][] intervals = new int[][] {{2, 6}, {1, 3}, {2, 7}, {8, 10}, {15, 18}};
        int[][] res = merge(intervals);
        System.out.print(Arrays.deepToString(res));
    }

    @Test
    public void test2() {
        int[][] intervals = new int[][] {{1, 4}, {0, 2}, {3, 5}};
        int[][] res = merge(intervals);
        System.out.print(Arrays.deepToString(res));
    }

    @Test
    public void test3() {
        int[][] intervals = new int[][] {{1, 4}, {2, 3}};
        int[][] res = merge(intervals);
        System.out.print(Arrays.deepToString(res));
    }

    @Test
    public void test4() {
        int[][] intervals = new int[][] {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] res = merge(intervals);
        System.out.print(Arrays.deepToString(res));

    }
}
