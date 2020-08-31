package August;

import java.util.ArrayList;
import java.util.List;

public class day20200817 {

    private static List<Integer> subTreeHeight = new ArrayList<>();

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     *
     * 本题中，一棵高度平衡二叉树定义为：
     *
     * 一个二叉树每个节点的左右两个子树的高度差的绝对值不超过1。
     *
     * 示例 1:
     *
     * 给定二叉树 [3,9,20,null,null,15,7]
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回 true 。
     *
     * 示例 2:
     *
     * 给定二叉树 [1,2,2,3,3,null,null,4,4]
     *
     *        1
     *       / \
     *      2   2
     *     / \
     *    3   3
     *   / \
     *  4   4
     *
     * 链接：https://leetcode-cn.com/problems/balanced-binary-tree
     */
    public static boolean isBalanced(TreeNode root) {
        visit(root, 0);
        if (subTreeHeight.size() == 0) {
            return true;
        }
        subTreeHeight.sort((node1, node2) -> {
            return node1 - node2;
        });
        int minHeight = subTreeHeight.get(0);
        int maxHeight = subTreeHeight.get(subTreeHeight.size() - 1);
        if (maxHeight - minHeight >= 2) {
            return false;
        } else {
            return true;
        }
    }

    public static void visit(TreeNode node, int height) {
        if (node == null) {
            subTreeHeight.add(height);
        } else {
            height ++;
            visit(node.left, height);
            visit(node.right, height);
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int value) {
            this.val = value;
        }
    }

    public static void main(String[] args) {
//        TreeNode node1 = new TreeNode(1);
//        TreeNode node2 = new TreeNode(1);
//        TreeNode node3 = new TreeNode(1);
//        TreeNode node4 = new TreeNode(1);
//        TreeNode node5 = new TreeNode(1);
//        node1.left = node2;
//        node1.right = node3;
//        node3.left = node4;
//        node3.right = node5;
        System.out.println(isBalanced(null));

    }
}
