import org.junit.Test;

public class q767b_ReorganizeString {
    public String reorganizeString(String s) {
        int[] counts = new int[26];
        int maxCount = 0, n = s.length();
        int oddIndex = 1, evenIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            int p = s.charAt(i) - 'a';
            counts[p]++;
            if (counts[p] > maxCount) {
                maxCount = counts[p];
            }
            maxCount = Math.max(maxCount, counts[p]);
        }
        if (maxCount > (n + 1) / 2) {
            return "";
        }
        char[] res = new char[n];
        for (int i = 0; i < 26; i++) {
            char c = (char) (i + 'a');
            while (counts[i] > 0 && counts[i] <= n / 2 && oddIndex < n) {
                res[oddIndex] = c;
                oddIndex += 2;
                counts[i]--;
            }
            while (counts[i] > 0) {
                res[evenIndex] = c;
                evenIndex += 2;
                counts[i]--;
            }
        }
        return new String(res);
    }

    @Test
    public void test() {
        System.out.println(reorganizeString("aaabb"));
        System.out.println(reorganizeString("aabbc"));
    }
}
