import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class q54_SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        LinkedList<Integer> res = new LinkedList<>();
        int m = matrix.length, n = matrix[0].length;
        int top = 0, left = 0, bottom = m - 1, right = n - 1;
        int row, col;
        while (top <= bottom && left <= right) {
            for (col = left; col <= right; col++) {
                res.add(matrix[top][col]);
            }
            for (row = top + 1; row <= bottom; row++) {
                res.add(matrix[row][right]);
            }
            if (bottom > top && right > left) {
                for (col = right - 1; col >= left; col--) {
                    res.add(matrix[bottom][col]);
                }
                for (row = bottom - 1; row > top; row--) {
                    res.add(matrix[row][left]);
                }
            }
            top++;
            left++;
            bottom--;
            right--;
        }
        return res;
    }

    @Test
    public void test1() {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        spiralOrder(matrix);
    }

    @Test
    public void test2() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        spiralOrder(matrix);
    }

    @Test
    public void test3() {
        int[][] matrix = {{7}, {9}, {6}};
        spiralOrder(matrix);
    }

    @Test
    public void test4() {
        int[][] matrix = {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}};
        spiralOrder(matrix);
    }
}
