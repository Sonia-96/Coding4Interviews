import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class q7b_ReverseInteger {
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            rev = rev * 10 + digit;
        }
        return rev;
    }

    @Test
    public void test() {
        assertEquals(321, reverse(123));
        assertEquals(-321, reverse(-123));
        assertEquals(0, reverse(0));
        assertEquals(0, reverse(-1534236469));
        assertEquals(-2143847412, reverse(-2147483412));
        assertEquals(0, reverse(1534236469));
    }
}
