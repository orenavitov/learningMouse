package August;

/**
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 *
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 *
 * 示例 1:
 *
 * 输入: [3,2,3,null,3,null,1]
 *
 *      3
 *     / \
 *    2   3
 *     \   \
 *      3   1
 *
 * 输出: 7
 * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
 * 示例 2:
 *
 * 输入: [3,4,5,1,3,null,1]
 *
 *      3
 *     / \
 *    4   5
 *   / \   \
 *  1   3   1
 *
 * 输出: 9
 * 解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
 *
 * 链接：https://leetcode-cn.com/problems/house-robber-iii
 */
public class day20200805 {
    public static int rob(TreeNode root) {

        return Math.max(steal(root), noSteal(root));
    }

    private static int steal(TreeNode treeNode) {
        if (treeNode != null) {
            return treeNode.val + noSteal(treeNode.left) + noSteal(treeNode.right);
        } else {
            return 0;
        }
    }

    private static int noSteal(TreeNode treeNode) {
        if (treeNode != null) {
            return Math.max(steal(treeNode.left), noSteal(treeNode.left)) +
                    Math.max(steal(treeNode.right), noSteal(treeNode.right));
        } else {
            return 0;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

    }

    public static void main(String[] args) {
        TreeNode treeNode1= new TreeNode(3);
        TreeNode treeNode2= new TreeNode(2);
        TreeNode treeNode3= new TreeNode(3);
        TreeNode treeNode4= new TreeNode(3);
        TreeNode treeNode5= new TreeNode(1);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.right = treeNode4;
        treeNode3.right = treeNode5;
        System.out.println(rob(treeNode1));
    }

}
