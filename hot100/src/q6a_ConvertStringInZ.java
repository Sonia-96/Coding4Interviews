import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q6a_ConvertStringInZ {
    // 方法一：推出每行字符下标的通项公式
    public String convert(String s, int numRows) {
        if (s.length() < 2 || numRows == 1 || numRows > s.length()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int cycleLen = 2 * numRows - 2;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; i + j < s.length(); j += cycleLen) {
                sb.append(s.charAt(i + j));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < s.length()) {
                    sb.append(s.charAt(j + cycleLen - i));
                }
            }
        }
        return sb.toString();
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
