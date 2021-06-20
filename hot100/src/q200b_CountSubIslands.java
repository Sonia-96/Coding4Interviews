import java.util.LinkedList;
import java.util.Queue;

public class q200b_CountSubIslands {
    // 广度优先搜索
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    queue.add(i * n + j);
                    while (!queue.isEmpty()) {
                        int p = queue.remove();
                        int row = p / n;
                        int col = p % n;
                        put(grid, queue, row - 1, col);
                        put(grid, queue, row + 1, col);
                        put(grid, queue, row, col - 1);
                        put(grid, queue, row, col + 1);
                    }
                    num += 1;
                }
            }
        }
        return num;
    }

    private void put(char[][] grid, Queue<Integer> queue, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        queue.add(i * n + j);
    }
}
