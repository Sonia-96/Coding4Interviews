import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class q1905b {
    // 广度优先搜索
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

    @Test
    public void test() {
        int[][] grids1 = {{1, 1, 1, 0, 0},{0, 1, 1, 1, 1},{0, 0, 0, 0, 0},{1, 0, 0, 0, 0},{1, 1, 0, 1, 1}};
        int[][] grids2 = {{1,1,1,0,0},{0,0,1,1,1},{0,1,0,0,0},{1,0,1,1,0},{0,1,0,1,0}};
        assertEquals(3, countSubIslands(grids1, grids2));
        int[][] grids3 = {{1,0,1,0,1}, {1,1,1,1,1}, {0,0,0,0,0}, {1,1,1,1,1}, {1,0,1,0,1}};
        int[][] grids4 = {{0,0,0,0,0}, {1,1,1,1,1}, {0,1,0,1,0}, {0,1,0,1,0}, {1,0,0,0,1}};
        assertEquals(2, countSubIslands(grids3, grids4));
        assertEquals(1, countSubIslands(new int[][] {{1, 0, 1}, {1, 1, 1}}, new int[][] {{1, 0, 1}, {1, 1, 1}}));
    }
}
