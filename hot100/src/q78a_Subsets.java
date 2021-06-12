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
            for (int idx = i, j = 0; idx > 0; idx >>= 1, j++) {
                if ((idx & 1) == 1) {
                    subset.add(nums[j]);
                }
            }
            res.add(subset);
        }
        return res;
    }
}
