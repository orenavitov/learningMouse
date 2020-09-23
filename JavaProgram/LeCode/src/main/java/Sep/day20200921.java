package Sep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day20200921 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> values = new ArrayList<>();
        if (root != null) {
            dfs(root, values);
        }
        Collections.sort(values);
        newDfs(root, values);
        return root;
    }

    private static void dfs(TreeNode node, List<Integer> values) {
        values.add(node.val);
        TreeNode left = node.left;
        TreeNode right = node.right;
        if (left != null) {
            dfs(left, values);
        }
        if (right != null) {
            dfs(right, values);
        }

    }

    private static void newDfs(TreeNode node, List<Integer> values) {
        int index = values.size();
        int val = node.val;
        for (int i = 0; i < values.size(); i ++) {
            int cur = values.get(i);
            if (val >= cur) {
                continue;
            } else {
                index = i;
                break;
            }
        }
        for (int i = index; i < values.size(); i ++) {
            val = val + values.get(i);
        }
        node.val = val;
        TreeNode left = node.left;
        TreeNode right = node.right;
        if (left != null) {
            newDfs(left, values);
        }
        if (right != null) {
            newDfs(right, values);
        }
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(13);
        node1.left = node2;
        node1.right = node3;
        TreeNode root = convertBST(node1);
        System.out.println(root.val);

    }

}
