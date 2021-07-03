import org.junit.Test;

import java.util.PriorityQueue;

public class q767a_ReorganizeString {
    public String reorganizeString(String s) {
        int[] counts = new int[26];
        int maxCount = 0, n = s.length();
        for (int i = 0; i < s.length(); i++) {
            int p = s.charAt(i) - 'a';
            counts[p]++;
            maxCount = Math.max(maxCount, counts[p]);
        }
        if (maxCount > (n + 1) >> 1) {
            return "";
        }
        PriorityQueue<Character> maxHeap = new PriorityQueue<>((c1, c2) -> counts[c2 - 'a'] - counts[c1 - 'a']);
        for (int i = 0; i < 26; i++) {
            if (counts[i] > 0) {
                maxHeap.add((char) (i + 'a'));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n >> 1; i++) {
            char c1 = maxHeap.poll();
            char c2 = maxHeap.poll();
            sb.append(c1);
            sb.append(c2);
            counts[c1 - 'a']--;
            counts[c2 - 'a']--;
            if (counts[c1 - 'a'] > 0) {
                maxHeap.add(c1);
            }
            if (counts[c2 - 'a'] > 0) {
                maxHeap.add(c2);
            }
        }
        if (!maxHeap.isEmpty()) {
            sb.append(maxHeap.poll());
        }
        return sb.toString();
    }

    @Test
    public void test() {
        System.out.println(reorganizeString("aaabb"));
        System.out.println(reorganizeString("aabbc"));
    }
}
