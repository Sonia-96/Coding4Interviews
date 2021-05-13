import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q6_ConvertStringInZ {
    public String convert(String s, int numRows) {
        if (s.length() < 2 || numRows == 1) return s;
        StringBuilder sb = new StringBuilder();
        int cycle = 2 * (numRows - 1);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < s.length(); j += cycle) {
                sb.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycle - i < s.length()) {
                    sb.append(s.charAt(j + cycle - i));
                }
            }

//            for (int j = i; j < s.length(); j += cycle) {
//                sb.append(s.charAt(j));
//                if (i != 0 && i != numRows - 1 && j + cycle - 2 * i < s.length()) {
//                    sb.append(s.charAt(j + cycle - 2 * i));
//                }
//            }
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
