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
            // parent同时记录父结点（非负数）和集合的size（负数）
            parent = new int[m * n];
            Arrays.fill(parent, -1);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if(grid[i][j] == '1') {
                        count += 1;
                    }
                }
            }
        }

        private int getCount() {
            return count;
        }

        private int find(int v) {
            int root = v;
            while (parent[root] > 0) {
                root = parent[root];
            }
            while (v != root) {
                int next = parent[v];
                parent[v] = root;
                v = next;
            }
            return root;
        }

        private int sizeOf(int v) {
            return -parent[v];
        }

        private void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 != root2) {
                int size1 = sizeOf(root1);
                int size2 = sizeOf(root2);
                if (size1 > size2) {
                    parent[root1] -= size2;
                    parent[root2] = root1;
                } else {
                    parent[root2] -= size1;
                    parent[root1] = root2;
                }
                count -= 1;
            }
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
