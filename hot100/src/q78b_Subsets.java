import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class q78b_Subsets {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> subset = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtrack(0, nums);
        return res;
    }

    public void backtrack(int i, int[] nums) {
        res.add(new ArrayList(subset));
        for (int j = i; j < nums.length; j++) {
            subset.add(nums[j]);
            backtrack(j + 1, nums);
            subset.remove(subset.size() - 1);
        }
    }

    @Test
    public void test() {
        System.out.println(subsets(new int[] {1, 2, 3}));
    }
}
