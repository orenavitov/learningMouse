package Sep;

public class day20200927 {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int rootVal = root.val;
        int pVal = p.val;
        int qVal = q.val;
        return findDepth(root, p, q);
    }

    public static TreeNode findDepth(TreeNode node, TreeNode p, TreeNode q) {
        int rootVal = node.val;
        int pVal = p.val;
        int qVal = q.val;
        if((pVal <= rootVal && qVal >= rootVal) ||
                (pVal >= rootVal && qVal <= rootVal)) {
            return node;
        }
        if (pVal < rootVal && qVal < rootVal) {
            return findDepth(node.left, p, q);
        }
        if (pVal > rootVal && qVal > rootVal) {
            return findDepth(node.right, p, q);
        }
        return node;
    }





    public static void main(String[] args) {
//        TreeNode node1 = new TreeNode(6);
//        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(8);
//        TreeNode node4 = new TreeNode(0);
//        TreeNode node5 = new TreeNode(4);
//        TreeNode node6 = new TreeNode(7);
//        TreeNode node7 = new TreeNode(9);
//        TreeNode node8 = new TreeNode(3);
//        TreeNode node9 = new TreeNode(5);
//        node1.left = node2;
//        node1.right = node3;
//        node2.left = node4;
//        node2.right = node5;
//        node3.left = node6;
//        node3.right = node7;
//        node5.left = node8;
//        node9.right = node9;


        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(6);
        TreeNode node4 = new TreeNode(2);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node4.left = node6;

        TreeNode parent = lowestCommonAncestor(node1, node6, node5);
        System.out.println(parent.val);
    }
}
