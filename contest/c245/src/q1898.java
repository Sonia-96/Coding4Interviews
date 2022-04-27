import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1898 {
    public int maximumRemovals(String s, String p, int[] removable) {
        int left = 0, right = removable.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (check(s, p, removable, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left - 1;
    }

    private boolean check(String s, String p, int[] removable, int k) {
        boolean[] removed = new boolean[s.length()];
        for (int i = 0; i < k; i++) {
            removed[removable[i]] = true;
        }
        int i = 0, j = 0;
        while (i < s.length() && j < p.length()) {
            if (!removed[i] && s.charAt(i) == p.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == p.length();
    }

    @Test
    public void test() {
        assertEquals(2, maximumRemovals("abcacb", "ab", new int[] {3, 1, 0}));
        assertEquals(1, maximumRemovals("abcbddddd", "abcd", new int[] {3,2,1,4,5,6}));
        assertEquals(0, maximumRemovals("abcab", "abc", new int[]{0, 1, 2, 3, 4}));
        assertEquals(2, maximumRemovals("qlevcvgzfpryiqlwy", "qlecfqlw", new int[] {12,5}));
    }
}
