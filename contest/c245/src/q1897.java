import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class q1897 {
    public boolean makeEqual(String[] words) {
        Map<Character, Integer> map = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                map.put(word.charAt(i), (map.getOrDefault(word.charAt(i), 0)) + 1);
            }
        }
        Set<Character> keySet = map.keySet();
        for (Character key : keySet) {
            int value = map.get(key);
            if (value % words.length != 0) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void test() {
        assertTrue(makeEqual(new String[] {"abc", "bc", "aabc"}));
        assertFalse(makeEqual(new String[] {"ab","a"}));
    }
}
