import java.util.ArrayList;
import java.util.List;

public class q94a_InorderTraversal {
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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorderTraversal(root, res);
        return res;
    }

    public void inorderTraversal(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, res);
        res.add(node.val);
        inorderTraversal(node.right, res);
    }
}
