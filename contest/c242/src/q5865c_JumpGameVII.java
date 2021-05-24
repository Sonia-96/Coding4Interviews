import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q5865c_JumpGameVII {
    // BFS + 指针优化
    public boolean canReach(String s, int minJump, int maxJump) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        int last = 0;
        while (!queue.isEmpty()) {
            int curr = queue.remove();
            if (curr == s.length() - 1 && s.charAt(curr) == '0') {
                return true;
            }
            int left = curr + minJump, right = Math.min(curr + maxJump, s.length() - 1);
            for (int i = Math.max(left, last + 1); i <= right; i++) {
                if (s.charAt(i) == '0') {
                    last = i;
                    queue.add(i);
                }
            }
        }
        return false;
    }

    @Test
    public void test() {
        assertTrue(canReach("011010", 2, 3));
        assertFalse(canReach("01101110", 2, 3));
        assertFalse(canReach("01", 1, 1));
        assertTrue(canReach("0000000000", 1, 5));
        assertFalse(canReach("00111010", 3, 5));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append('0');
        }
        String s = sb.toString();
        assertTrue(canReach(s, 1, 100000));
    }
}
