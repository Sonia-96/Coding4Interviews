import org.junit.Test;

public class q151_ReverseWords {
    public String reverseWords(String s) {
        StringBuilder res = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.charAt(i) == ' ') {
                if (!word.isEmpty()) {
                    String curr = word.toString();
                    if (isWord(curr)) {
                        res.append(res.isEmpty() ? reverse(curr) : " " + reverse(curr));
                    } else {
                        res.append(res.isEmpty() ? curr : " " + curr);
                    }
                }
                word = new StringBuilder();
            } else {
                word.append(s.charAt(i));
            }
        }
        return res.toString();
    }

    private boolean isWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
                return false;
            }
        }
        return true;
    }

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    @Test
    public void test() {
        System.out.println(reverseWords("a !hello abc"));
        System.out.println(reverseWords("hhha"));
    }
}
