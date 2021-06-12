import java.util.ArrayList;
import java.util.List;

public class q34_pathSum {
     public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }

    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        backtrack(root, target);
        return res;
    }

    private void backtrack(TreeNode node, int target) {
        if (node == null) {
            return;
        }
        path.add(node.val);
        boolean isLeaf = node.left == null && node.right == null;
        if (isLeaf && node.val == target) {
            res.add(new ArrayList(path));
        }
        backtrack(node.left, target - node.val);
        backtrack(node.right, target - node.val);
        path.remove(path.size() - 1);
    }
}
