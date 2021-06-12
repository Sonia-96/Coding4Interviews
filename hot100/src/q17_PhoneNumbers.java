import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class q17_PhoneNumbers {
    String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return res;
        }
        recursion(digits, 0, new StringBuilder());
        return res;
    }

    private void recursion(String digits, int i, StringBuilder cur) {
        if (i == digits.length()) {
            res.add(cur.toString());
            return;
        }
        String letters = map[Character.getNumericValue(digits.charAt(i))];
        for (int j = 0; j < letters.length(); j++) {
            cur.append(letters.charAt(j));
            recursion(digits, i + 1, cur);
            cur.deleteCharAt(i);
        }
    }

    @Test
    public void test() {
        System.out.println(letterCombinations(""));
        List<String> ans = letterCombinations("234");
        System.out.println(ans);
        System.out.println(ans.size());
    }
}
