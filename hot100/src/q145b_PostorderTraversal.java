import org.junit.Test;

import java.util.*;

public class q145b_PostorderTraversal {
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

    //迭代写法
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode prev = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.add(root);
                root = root.left;
            }
            root = stack.removeLast();
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            } else {
                stack.add(root);
                root = root.right;
            }
        }
        return res;
    }

    private TreeNode initTree(int[] nums) {
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.offer(new TreeNode(nums[0]));
        TreeNode root = nodes.peek();
        int i = 0;
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            int lval = nums[++i]; // 先+1再赋值
            int rval = nums[++i];
            if (lval >= 0) {
                TreeNode l = new TreeNode(lval);
                node.left = l;
                nodes.offer(l);
            }
            if (rval >= 0) {
                TreeNode r = new TreeNode(rval);
                node.right = r;
                nodes.offer(r);
            }
        }
        return root;
    }

    @Test
    public void test() {
        TreeNode root = initTree(new int[] {1, -1, 2, 3, -1, -1, -1});
        System.out.println(postorderTraversal(root));
    }
}
