import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q7a_ReverseInteger {
    public int reverse(int x) {
        int flag = x < 0 ? -1 : 1; //标记正负数
        String s = String.valueOf(Math.abs(x)); // 将数值转换为字符串
        // 翻转数值
        int i = s.length() - 1;
        while (s.charAt(i) == 0) { //忽略末尾的0
            i -= 1;
        }
        char[] rev = new char[i + 1];
        for (int j = 0; j < rev.length && i >= 0; j++, i--) {
            rev[j] = s.charAt(i);
        }
        // 判断数值溢出
        char[] maxValue = String.valueOf(Integer.MAX_VALUE).toCharArray();
        if (flag < 0) {
            maxValue[9] = '8';
        }
        if (rev.length > 10) return 0;
        if (rev.length == 10) {
            for (int j = 0; j < 10; j++) {
                if (rev[j] > maxValue[j]) {
                    return 0;
                } else if (rev[j] < maxValue[j]) {
                    break;
                }
            }
        }
        return Integer.parseInt(String.valueOf(rev)) * flag;
    }

    @Test
    public void test() {
        assertEquals(321, reverse(123));
        assertEquals(-321, reverse(-123));
        assertEquals(0, reverse(0));
        assertEquals(0, reverse(-1534236469));
        assertEquals(-2143847412, reverse(-2147483412));
    }
}
