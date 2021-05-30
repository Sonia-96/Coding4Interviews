import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q5773 {
    public String maxValue(String n, int x) {
        int i;
        if (n.charAt(0) == '-') {
            i = 1;
            while (i < n.length() && Character.getNumericValue(n.charAt(i)) <= x) {
                i+= 1;
            }
        } else {
            i = 0;
            while (i < n.length() && Character.getNumericValue(n.charAt(i)) >= x) {
                i+= 1;
            }
        }
        return n.substring(0, i) + Character.forDigit(x, 10) + n.substring(i);
    }

    @Test
    public void test() {
        assertEquals("763", maxValue("73", 6));
        assertEquals("999", maxValue("99", 9));
        assertEquals("-123", maxValue("-13", 2));
        assertEquals("-255", maxValue("-55", 2));
    }
}
