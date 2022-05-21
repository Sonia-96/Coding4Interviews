import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class q362a_HitCounter {
    static class HitCounter {
        Queue<Integer> hits;

        /** Initialize your data structure here. */
        public HitCounter() {
            hits = new LinkedList<>();
        }

        /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
        public void hit(int timestamp) {
            remove(timestamp);
            hits.offer(timestamp);
        }

        /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
        public int getHits(int timestamp) {
            remove(timestamp);
            return hits.size();
        }

        private void remove(int timestamp) {
            if (hits.isEmpty()) return;
            while (hits.peek() + 300 <= timestamp) {
                hits.poll();
            }
        }
    }

    @Test
    public void test() {
        HitCounter hc = new HitCounter();
        hc.hit(1);
        hc.hit(2);
        hc.hit(3);
        System.out.println(hc.getHits(4));
        hc.hit(300);
        System.out.println(hc.getHits(300));
        System.out.println(hc.getHits(301));
    }
}
