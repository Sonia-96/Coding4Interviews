import java.util.ArrayList;
import java.util.List;

public class q145a_PostTraversal {
    class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 递归写法
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postorderTraversal(root, res);
        return res;
    }

    private void postorderTraversal(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        postorderTraversal(node.left, res);
        postorderTraversal(node.right, res);
        res.add(node.val);
    }
}
