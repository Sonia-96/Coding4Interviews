import org.junit.Test;

import java.util.Arrays;

public class q59b_SpiralMatrix2 {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int d = 0, num = 1;
        int row = 0, col = 0, nextRow, nextCol;
        while (num <= n * n) {
            res[row][col] = num++;
            nextRow = Math.floorMod(row + directions[d][0], n);
            nextCol = Math.floorMod(col + directions[d][1], n);

            if (res[nextRow][nextCol] != 0) {
                d = (d + 1) % 4;
            }

            row += directions[d][0];
            col += directions[d][1];
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println(Math.floorDiv(-1, 3));
        System.out.println(Math.floorMod(-1, -3));
        System.out.println((-2) % (-3));
        System.out.println(Arrays.deepToString(generateMatrix(3)));
    }
}
