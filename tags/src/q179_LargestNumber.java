import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class q179_LargestNumber {

    public String largestNumber(int[] nums) {
        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = Integer.toString(nums[i]);
        }
        Arrays.sort(strings, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));
        if (strings[0].equals("0")) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        for (String s : strings) {
            res.append(s);
        }
        return res.toString();
    }

    @Test
    public void test1() {
        int[] nums = {3, 30, 34, 5, 9};
        Assert.assertEquals("9534330", largestNumber(nums));
    }

    @Test
    public void test2() {
        int[] nums = {432,43243};
        Assert.assertEquals("43243432", largestNumber(nums));
    }

    @Test
    public void test3() {
        int[] nums = {0,0};
        Assert.assertEquals("0", largestNumber(nums));
    }
}
