import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1864_MinSwaps {
    public int minSwaps(String s) {
        int num0 = 0, num1 = 0, odd1 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                num0 += 1;
            } else {
                num1 += 1;
                if ((i & 1) == 1) {
                    odd1 += 1;
                }
            }
        }
        if (Math.abs(num0 - num1) > 1) {
            return -1;
        }
        if (num0 == num1) {
            return Math.min(num1 - odd1, odd1);
        } else if (num0 > num1) { // 目标字符串中1全在奇数位
            return num1 - odd1;
        } else { // 目标字符串中1全在偶数位
            return odd1;
        }
    }

    @Test
    public void test() {
        assertEquals(1, minSwaps("111000"));
        assertEquals(0, minSwaps("010"));
        assertEquals(-1, minSwaps("1110"));
    }
}
