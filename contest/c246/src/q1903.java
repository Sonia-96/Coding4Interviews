import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1903 {
    public String largestOddNumber(String num) {
        int end = 0;
        // 从右到左遍历，找到第一个奇数即可返回
        for (int i = num.length() - 1; i >= 0 ; i--) {
            if ((num.charAt(i) & 1) == 1) {
                end = i + 1;
                break;
            }
        }
        return num.substring(0, end);
    }

    @Test
    public void test() {
        assertEquals("5", largestOddNumber("52"));
        assertEquals("", largestOddNumber("4206"));
        assertEquals("35427", largestOddNumber("35427"));
        for (int i = 0; i < 10; i++) {
            int tmp = (int) Character.forDigit(i, 10);
            if ((tmp & 1) != (i & 1)) {
                System.out.println("0~9的字符与其ASCII码值奇偶性不一定相同！");
                break;
            }
        }
        System.out.println("0~9的字符与其ASCII码值奇偶性相同！");
    }
}
