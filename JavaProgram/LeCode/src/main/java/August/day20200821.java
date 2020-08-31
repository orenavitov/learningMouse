package August;

import java.util.ArrayList;
import java.util.LinkedList;

public class day20200821 {
 public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
    /**
     * 给定一个二叉树，找出其最小深度。
     *
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
     * 说明:叶子节点是指没有子节点的节点。
     *
     * 示例:
     *
     * 给定二叉树[3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最小深度 2.
     *
     * 链接：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree
     */

    private static int minHeight = Integer.MAX_VALUE;

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root, 1);
        return minHeight;
    }

    private static void dfs(TreeNode node, int curHeight) {

        TreeNode leftChild = node.left;
        TreeNode rightChild = node.right;
        if (leftChild == null && rightChild == null) {
            if (curHeight < minHeight) {
                minHeight = curHeight;
            }
        } else {
            curHeight ++;
            if (leftChild != null) {
                dfs(leftChild, curHeight);
            }
            if (rightChild != null) {
                dfs(rightChild, curHeight);
            }
        }




    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
//        TreeNode node3 = new TreeNode(20);
//        TreeNode node4 = new TreeNode(15);
//        TreeNode node5 = new TreeNode(7);
//        TreeNode node5 = new TreeNode(3);
        node1.left = node2;
//        node1.right = node3;
//
//        node3.left = node4;
//        node3.right = node5;
        minDepth(node1);
        System.out.println(minHeight);
    }
}
