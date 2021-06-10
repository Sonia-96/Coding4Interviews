import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q11_ContainerWithMostWater {
    public int maxArea(int[] height) {
        int res = 0;
        for (int i = 0, j = height.length - 1; i < j; ) {
            // i++ : 先返回 i，再执行 i = i + 1
            int minHeight = height[i] < height[j] ? height[i++] : height[j--];
            int s = (j - i + 1) * minHeight;
            res = Math.max(res, s);
        }
        return res;
    }

    @Test
    public void test() {
        assertEquals(36, maxArea(new int[] {2,3,10,5,7,8,9}));
        assertEquals(17, maxArea(new int[] {2,3,4,5,18,17,6}));
    }
}
