import org.junit.Test;

import java.util.Arrays;

public class q59a_SpiralMatrix2 {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int num = 1;
        int top = 0, bottom = n - 1;
        int row, col;
        while (top <= bottom) {
            for (col = top; col <= bottom; col++) {
                res[top][col] = num++;
            }
            for (row = top + 1; row <= bottom; row++) {
                res[row][bottom] = num++;
            }
            if (bottom > top) {
                for (col = bottom - 1; col >= top; col--) {
                    res[bottom][col] = num++;
                }
                for (row = bottom - 1; row > top; row--) {
                    res[row][top] = num++;
                }
            }
            top++;
            bottom--;
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println(Arrays.deepToString(generateMatrix(3)));
    }
}
