import java.util.ArrayList;
import java.util.List;

public class q78d_Subsets {
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res1 = new ArrayList<>();
        preOrder(nums, 0, new ArrayList<>(), res1);
        System.out.println(res1);

        List<List<Integer>> res2 = new ArrayList<>();
        inOrder(nums, 0, new ArrayList<>(), res2);
        System.out.println(res2);

        List<List<Integer>> res3 = new ArrayList<>();
        postOrder(nums, 0, new ArrayList<>(), res3);
        System.out.println(res3);
        return null;
    }
    /**
     * DFS，前序遍历
     */
    public static void preOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) return;
        // 到了新的状态，记录新的路径，要重新拷贝一份
        subset = new ArrayList<Integer>(subset);

        // 这里
        res.add(subset);
        preOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);
        preOrder(nums, i + 1, subset, res);
    }

    /**
     * DFS，中序遍历
     */
    public static void inOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) return;
        subset = new ArrayList<Integer>(subset);

        inOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);
        // 这里
        res.add(subset);
        inOrder(nums, i + 1, subset, res);
    }

    /**
     * DFS，后序遍历
     */
    public static void postOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) return;
        subset = new ArrayList<Integer>(subset);

        postOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);
        postOrder(nums, i + 1, subset, res);
        // 这里
        res.add(subset);
    }

    public static void main(String[] args) {
        subsets(new int[] {1, 2, 3});
    }
}
