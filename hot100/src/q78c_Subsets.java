import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class q78c_Subsets {
    public List<List<Integer>> res = new ArrayList<>();
    public List<Integer> subset = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        dfs(0, nums);
        return res;
    }

    public void dfs(int i, int[] nums) {
        if (i == nums.length) {
            res.add(new ArrayList<>(subset));
            return;
        }
        // 选中当前数字
        subset.add(nums[i]);
        dfs(i + 1, nums);
        // 不选当前数字
        subset.remove(subset.size() - 1);
        dfs(i + 1, nums);
    }


    @Test
    public void test() {
        System.out.println(subsets(new int[] {1, 2, 3}));
    }
}
