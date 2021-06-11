import org.junit.Test;

public class q10_timeAnalysis {
    int[][] record; // 记录match(s, i, p, j)被执行的次数
    public boolean isMatch(String s, String p) {
        record = new int[s.length() + 1][p.length() + 1];
        return match(s, 0, p, 0);
    }

    private boolean match(String s, int i, String p, int j) {
        record[i][j] += 1;
        if (j == p.length()) {
            return i == s.length();
        }
        boolean firstMatch = (i != s.length()) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            if (firstMatch) {
                return match(s, i + 1, p, j) || match(s, i, p, j + 2);
            }
            return match(s, i, p, j + 2);
        }
        return firstMatch && match(s, i + 1, p, j + 1);
    }

    private void print2DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print("i = " + i + "  ");
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void test() {
        String s = "aaaaa";
        String p = "a*a*a*aaaaa";
        System.out.println(isMatch(s, p));
        print2DArray(record);
    }
}
