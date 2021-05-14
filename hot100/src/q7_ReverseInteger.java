import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q7_ReverseInteger {
    public int reverse(int x) {
        int flag = x < 0 ? -1 : 1;
        String s = String.valueOf(Math.abs(x));
        int i = s.length() - 1;
        while (s.charAt(i) == 0) {
            i -= 1;
        }
        char[] tmp = new char[i + 1];
        for (int j = 0; j < tmp.length && i >= 0; j++, i--) {
            tmp[j] = s.charAt(i);
        }
        char[] maxValue = String.valueOf(Integer.MAX_VALUE).toCharArray();
        if (flag < 0) {
            maxValue[9] = '8';
        }
        if (tmp.length > 10) return 0;
        if (tmp.length == 10) {
            for (int j = 0; j < 10; j++) {
                if (tmp[j] > maxValue[j]) {
                    return 0;
                } else if (tmp[j] < maxValue[j]) {
                    break;
                }
            }
        }
        int res = Integer.parseInt(String.valueOf(tmp));
        return res * flag;
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
