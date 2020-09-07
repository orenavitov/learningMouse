package Sep;

import java.util.ArrayList;
import java.util.List;

public class day20200904 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        String path = root.val + "";
        if (root.left == null && root.right == null) {
            result.add(path);
            return result;
        }

        if (root.left != null) {
            dfs(root.left, path, result);
        }
        if (root.right != null) {
            dfs(root.right, path, result);
        }
        return result;
    }

    private static void dfs(TreeNode node, String path, List<String> result) {
        String temp = path;
        path = path + "->" + node.val;
        if (node.left == null && node.right == null) {
            result.add(path);
            return;
        }

        if (node.left != null) {
            dfs(node.left, path, result);
        }
        if (node.right != null) {
            dfs(node.right, path, result);
        }

        path = temp;


    }


    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        List<String> result = binaryTreePaths(node1);
        result.forEach(path -> {
            System.out.println(path);
        });

    }
}
