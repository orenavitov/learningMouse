package Sep;

public class day20200922 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static int minCameraCover(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.min(install(root), uninstall(root, false));
    }

    private static int install(TreeNode node) {
        TreeNode left = node.left;
        TreeNode right = node.right;
        int result1 = 0;
        int result2 = 0;
        if (left != null) {
            result1 = Math.min(uninstall(left, true), install(left));
        }
        if (right != null) {
            result2 = Math.min(uninstall(right, true), install(right));
        }
        return 1 + result1 + result2;
    }

    private static int uninstall(TreeNode node, boolean covered) {
        TreeNode left = node.left;
        TreeNode right = node.right;
        int result1 = 0;
        int result2 = 0;
        if (left != null && right == null) {
            if (covered) {
                return Math.min(uninstall(left, false), install(left));
            } else {
                return install(left);
            }
        }
        if (left == null && right != null) {
            if (covered) {
                return Math.min(uninstall(right, false), install(right));
            } else {
                return install(right);
            }
        }
        if (left != null && right != null) {
            if (covered) {
//                result1 = Math.min(install(left), uninstall(left, false));
                result1 = install(left) + uninstall(right, false);
//                result2 = Math.min(install(right), uninstall(right, false));
                result2 = install(right) + uninstall(left, false);

                result1 = Math.min(result1, result2);
                result2 = uninstall(left, false) + uninstall(right, false);
                return Math.min(result1, result2);
            } else {
//                int temp1 = Math.min(install(left), uninstall(right, false));
                int temp1 = install(left) + uninstall(right, false);
//                int temp2 = Math.min(install(right), uninstall(left, false));
                int temp2 = install(right) + uninstall(left, false);
                result1 = Math.min(temp1, temp2);
                result2 = install(left) + install(right);

                return Math.min(result1, result2);

            }

        }
        if (left == null && right == null && covered == false) {
            return 1;
        }
        return result1 + result2;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(0);
        TreeNode node2 = new TreeNode(0);
        TreeNode node3 = new TreeNode(0);
        TreeNode node4 = new TreeNode(0);
        TreeNode node5 = new TreeNode(0);
        TreeNode node6 = new TreeNode(0);
        TreeNode node7 = new TreeNode(0);
        TreeNode node8 = new TreeNode(0);
        TreeNode node9 = new TreeNode(0);
        TreeNode node10 = new TreeNode(0);

//        node1.left = node2;
//        node2.left = node3;
//        node2.right = node4;

//        node1.left = node2;
//        node2.left = node3;
//        node3.left = node4;
//        node4.left = node5;

//        node1.left = node2;
//        node1.right = node3;
//        node3.right = node4;

//        node1.right = node2;
//        node2.right = node3;
//        node3.right = node4;
//        node4.left = node5;
//        node4.right = node6;
//        node6.left = node7;
//        node6.right = node8;

        node1.right = node2;
        node2.left = node3;
        node2.right = node4;
        node3.left = node5;
        node3.right = node6;
        node5.right = node7;
        node6.left = node8;
        node7.right = node9;
        node8.left = node10;

        System.out.println(minCameraCover(node1));
    }
}
