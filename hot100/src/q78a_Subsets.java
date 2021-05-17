import java.util.ArrayList;
import java.util.List;

// Method 1: Binary Bit
public class q78a_Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int length = nums.length;
        int n = 1 << length;
        for (int i = 0; i < n; i++) {
            List<Integer> subset = new ArrayList<>();
            int index = i;
            for (int j = 0; j < length; j++) {
                if ((index & 1 )== 1) {
                    subset.add(nums[j]);
                }
                index >>= 1;
            }
            res.add(subset);
        }
        return res;
    }
}
