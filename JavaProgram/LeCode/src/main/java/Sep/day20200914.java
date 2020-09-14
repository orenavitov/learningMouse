package Sep;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class day20200914 {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static List<Integer> inorderTraversal_(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        dfs(root, result);
        return result;
    }

    private static void dfs(TreeNode root, List<Integer> result) {
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (left != null) {
            dfs(left, result);
        }
        result.add(root.val);
        if (right != null) {
            dfs(right, result);
        }
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        TreeNode left = root.left;
        if (left != null) {
            stack.push(left);
            middleTravesal(stack, result);
        }
        result.add(root.val);
        TreeNode right = root.right;
        if (right != null) {
            stack.push(right);
            middleTravesal(stack, result);
        }
        return result;


    }

    public static void middleTravesal(Stack<TreeNode> stack, List<Integer> result) {
        Stack<TreeNode> stack1 = new Stack<>();
        while (!stack.isEmpty()) {
            TreeNode top = stack.pop();

            TreeNode leftChild = top.left;
            if (leftChild != null) {
                stack.push(leftChild);

            }
            TreeNode rightChild = top.right;
            if (rightChild != null) {
//                stack.push(rightChild);
                stack1.push(rightChild);

            }
            stack1.push(top);
        }
        while (!stack1.isEmpty()) {
            result.add(stack1.pop().val);
        }
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
//        TreeNode node4 = new TreeNode(4);
//        TreeNode node5 = new TreeNode(5);
//        TreeNode node6 = new TreeNode(6);
//        TreeNode node7 = new TreeNode(7);
//        node1.left = node2;
//        node1.right = node3;
//        node2.left = node4;
//        node2.right = node5;
//        node3.left = node6;
//        node3.right = node7;
        node3.left = node1;
        node1.right = node2;
        List<Integer> result = inorderTraversal_(node3);
        System.out.println(result.toString());
    }
}
