import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1870_minSpeedOnTime {
    public int minSpeedOnTime(int[] dist, double hour) {
        if (dist.length - 1 >= hour) {
            return -1;
        }
        int minV = 1;
        int maxV = 10000000;
        while (minV < maxV) {
            int midV = (minV + maxV) / 2;
            double time = getTime(dist, midV);
            if (time <= hour) {
                maxV = midV;
            } else {
                minV = midV + 1;
            }
        }
        return minV;
    }

    public double getTime(int[] dist, int midV) {
        double t = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            t += Math.ceil((dist[i] + 0.0)/ midV);
        }
        t += (dist[dist.length - 1] + 0.0) / midV;
        return t;
    }

    @Test
    public void test() {
        assertEquals(1, minSpeedOnTime(new int[] {1, 3, 2}, 6), 0.001);
        assertEquals(3, minSpeedOnTime(new int[] {1, 3, 2}, 2.7), 0.001);
        assertEquals(-1, minSpeedOnTime(new int[] {1, 3, 2}, 1.9), 0.001);
        assertEquals(10000000, minSpeedOnTime(new int[] {1, 1, 100000}, 2.01));
    }
}
