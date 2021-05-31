import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q6b_ConvertStringInZ {
    // 方法二：将字符放到对应的行里，再合并各行
    public String convert(String s, int numRows) {
        if (s.length() < 2 || numRows == 1 || numRows > s.length()) {
            return s;
        }
        // 初始化
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        // 从左到右遍历字符串，并将字符放到对应的行里
        int currRow = 0, increment = -1;
        for (int i = 0; i < s.length(); i++) {
            rows[currRow].append(s.charAt(i));
            if (currRow == numRows - 1 || currRow == 0) {
                increment *= -1;
            }
            currRow += increment;
        }
        // 按顺序合并各行的字符串
        StringBuilder res = new StringBuilder();
        for (StringBuilder row: rows) {
            res.append(row);
        }
        return res.toString();
    }

    @Test
    public void test() {
        assertEquals("PAHNAPLSIIGYIR", convert("PAYPALISHIRING", 3));
        assertEquals("PINALSIGYAHRPI", convert("PAYPALISHIRING", 4));
        assertEquals("a", convert("a", 1));
        assertEquals("", convert("", 1));
        assertEquals("AB", convert("AB", 1));
        assertEquals("AB", convert("AB", 4));
    }
}