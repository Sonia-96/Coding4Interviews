import org.junit.Test;

public class q362b_HitCounter {
    static class HitCounter {
        final int WINDOW_SIZE = 300;
        int[] times;
        int[] hits;

        /** Initialize your data structure here. */
        public HitCounter() {
            times = new int[WINDOW_SIZE];
            hits = new int[WINDOW_SIZE];
        }

        /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
        public void hit(int timestamp) {
            int index = timestamp % WINDOW_SIZE;
            if (times[index] == timestamp) {
                hits[index] += 1;
            } else {
                times[index] = timestamp;
                hits[index] = 1;
            }
        }

        /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
        public int getHits(int timestamp) {
            int count = 0;
            for (int i = 0; i < WINDOW_SIZE; i++) {
                if (timestamp - times[i] < WINDOW_SIZE) {
                    count += hits[i];
                }
            }
            return count;
        }
    }

    @Test
    public void test() {
        HitCounter hc = new HitCounter();
        hc.hit(1);
        hc.hit(2);
        hc.hit(3);
        System.out.println(hc.getHits(4));
        System.out.println(hc.getHits(299));
        hc.hit(300);
        System.out.println(hc.getHits(300));
        System.out.println(hc.getHits(301));
    }

}
