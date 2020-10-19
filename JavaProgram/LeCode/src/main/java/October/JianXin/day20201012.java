package October.JianXin;

import java.util.ArrayList;
import java.util.List;

public class day20201012 {

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int getMinimumDifference(TreeNode root) {
        List<Integer> nums = new ArrayList<>();
        dfs(root, nums);
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < nums.size() - 1; i ++) {
            int next = nums.get(i + 1);
            int cur = nums.get(i);
            if (next - cur < min) {
                min = next - cur;
            }
        }
        return min;
    }

    private static void dfs(TreeNode node, List<Integer> nums) {
        if (node.left != null) {
            dfs(node.left, nums);
        }
        nums.add(node.val);
        if(node.right != null) {
            dfs(node.right, nums);
        }
    }

    public static void main(String[] args) {

    }
}
