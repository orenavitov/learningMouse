package Sep;

import java.util.*;

public class day20200906 {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        Stack<List<Integer>> tempResults = new Stack<>();
        List<List<Integer>> results = new ArrayList<>();
        if(root == null) {
            return results;
        }
        ArrayList nodes = new ArrayList();
        nodes.add(root);
        bfs(nodes, tempResults);

        while (!tempResults.isEmpty()) {
            results.add(tempResults.pop());
        }
        return results;
    }

    private static void bfs(List<TreeNode> nodes, Stack<List<Integer>> results) {
        List<Integer> temp = new ArrayList<>();
        int length = nodes.size();
        for (int i = 0; i < length; i ++) {
            TreeNode node = nodes.remove(0);
            temp.add(node.val);
            TreeNode left = node.left;
            TreeNode right = node.right;
            if (left != null) {
                nodes.add(left);
            }
            if (right != null) {
                nodes.add(right);
            }
        }
        results.push(temp);
        if (!nodes.isEmpty()) {
            bfs(nodes, results);
        }
    }

    /**
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     *
     * 示例:
     *
     * 输入: [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     * 从下标为 0 跳到下标为 1 的位置，跳1步，然后跳3步到达数组的最后一个位置。
     * 说明:
     * 假设你总是可以到达数组的最后一个位置。
     * 链接：https://leetcode-cn.com/problems/jump-game-ii
     * @param nums
     */
    public static int jump(int[] nums) {
        int length = nums.length;
        int[] results = new int[length];
        for (int i = length - 2; i >= 0; i --) {
            if (i + nums[i] >= length - 1) {
                results[i] = 1;
                continue;
            } else {
                int min = length;
                for (int j = i + 1; j <= i + nums[i]; j ++) {
                    if (results[j] + 1 < min) {
                        min = results[j] + 1;
                    }
                }
                results[i] = min;
            }
        }
        return results[0];
    }



    public static void main(String[] args) {
//        TreeNode node1 = new TreeNode(3);
//        TreeNode node2 = new TreeNode(9);
//        TreeNode node3 = new TreeNode(20);
//        TreeNode node4 = new TreeNode(15);
//        TreeNode node5 = new TreeNode(7);
//        node1.left = node2;
//        node1.right = node3;
//        node3.left = node4;
//        node3.right = node5;
//        List<List<Integer>> results = levelOrderBottom(node1);
//        results.forEach(result -> {
//            System.out.println(result.toString());
//        });
        int[] nums = new int[]{2,3,0,1,4};
        int result = jump(nums);
        System.out.println(result);
    }
}
