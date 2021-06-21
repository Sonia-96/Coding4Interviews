import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class q200c_CountSubIslands {
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    grid[i][j] = '0';
                    if (i - 1 >= 0 && grid[i - 1][j] == '1') {
                        uf.union(i * n + j, (i - 1) * n + j);
                    }
                    if (i + 1 < m && grid[i + 1][j] == '1') {
                        uf.union(i * n + j, (i + 1) * n + j);
                    }
                    if (j - 1 >= 0 && grid[i][j - 1] == '1') {
                        uf.union(i * n + j, i * n + j - 1);
                    }
                    if (j + 1 < n && grid[i][j + 1] == '1') {
                        uf.union(i * n + j, i * n + j + 1);
                    }
                }
            }
        }
        return uf.getCount();
    }

    private class UnionFind {
        int[] parent;
        int count = 0;

        private UnionFind(char[][] grid) {
            int m = grid.length, n = grid[0].length;
            // 负值表示该结点为根节点，其绝对值为该并查集树的size
            parent = new int[m * n];
            Arrays.fill(parent, -1);
            // count初始化，每一个格子‘1’为一块单独的岛屿
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        count += 1;
                    }
                }
            }
        }

        private int find(int v) {
            int root = v;
            while (parent[root] >= 0) {
                root = parent[root];
            }
            // 路径压缩
            while (v != root) {
                int next = parent[v];
                parent[v] = root;
                v = next;
            }
            return root;
        }

        private int getSize(int v) {
            return -parent[find(v)];
        }

        private void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 != root2) {
                int size1 = getSize(root1);
                int size2 = getSize(root2);
                if (size1 < size2) {
                    parent[root1] = root2;
                    parent[root2] -= size1;
                } else {
                    parent[root2] = root1;
                    parent[root1] -= size2;
                }
                count -= 1;
            }
        }

        private int getCount() {
            return count;
        }
    }

    @Test
    public void test() {
        char[][] grid = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        assertEquals(1, numIslands(grid));
    }
}
