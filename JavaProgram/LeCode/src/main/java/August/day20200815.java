package August;



import java.util.ArrayList;

public class day20200815 {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 给定一个二叉树，原地将它展开为一个单链表。
     * 例如，给定二叉树
     *     1
     *    / \
     *   2   5
     *  / \   \
     * 3   4   6
     * 将其展开为：
     * 1
     *  \
     *   2
     *    \
     *     3
     *      \
     *       4
     *        \
     *         5
     *          \
     *           6
     *
     * 链接：https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list
     */
    private static ArrayList<TreeNode> treeNodes = new ArrayList<>();

    private static void flatten(TreeNode root) {
        visit(root);
        root = treeNodes.get(0);
        TreeNode curNode = root;
        for(int i = 1; i < treeNodes.size(); i ++) {
            TreeNode leftChild = new TreeNode(treeNodes.get(i).val);
            curNode.left = leftChild;
            curNode.right = null;
            curNode = leftChild;
        }
        curNode.left = null;
        curNode.right = null;

    }

    private static void visit(TreeNode curRoot) {
        if (curRoot != null) {
            treeNodes.add(curRoot);
            visit(curRoot.left);
            visit(curRoot.right);
        }
    }



    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(5);
        TreeNode treeNode4 = new TreeNode(3);
        TreeNode treeNode5 = new TreeNode(4);
        TreeNode treeNode6 = new TreeNode(6);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        flatten(treeNode1);
    }


}
