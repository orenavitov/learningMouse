package Sep.goodFuture;

import java.util.Stack;

public class Test1 {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static String notReCuPreOrder (TreeNode root) {
        // write code here
        StringBuilder result = new StringBuilder();
        TreeNode top = root;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            int val = cur.val;
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
            result.append(val);
            result.append(",");
        }

        return result.substring(0, result.length() - 1);

    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right =node5;
        String result = notReCuPreOrder(node1);
        System.out.println(result);
    }
}
