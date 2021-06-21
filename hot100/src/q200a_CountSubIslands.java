public class q200a_CountSubIslands {
    // 深度优先搜索
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    num += 1;
                }
            }
        }
        return num;
    }

    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i >= 0 && i < m && j >= 0 && j < n && grid[i][j] == '1') {
            grid[i][j] = '0'; // 标记该元素已被访问过
            dfs(grid, i - 1, j);
            dfs(grid, i + 1, j);
            dfs(grid, i, j - 1);
            dfs(grid, i, j + 1);
        }
    }
}
