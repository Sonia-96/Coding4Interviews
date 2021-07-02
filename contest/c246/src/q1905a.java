import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1905a {
    // 深度优先搜索
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

    @Test
    public void test() {
        int[][] grids1 = {{1, 1, 1, 0, 0},{0, 1, 1, 1, 1},{0, 0, 0, 0, 0},{1, 0, 0, 0, 0},{1, 1, 0, 1, 1}};
        int[][] grids2 = {{1,1,1,0,0},{0,0,1,1,1},{0,1,0,0,0},{1,0,1,1,0},{0,1,0,1,0}};
        assertEquals(3, countSubIslands(grids1, grids2));
    }
}
