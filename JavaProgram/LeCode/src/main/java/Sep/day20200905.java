package Sep;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class day20200905 {

    /**
     * 给出集合[1,2,3,…,n]，其所有元素共有n! 种排列。
     * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * 给定n 和k，返回第k个排列。
     * 说明：
     * 给定 n的范围是 [1, 9]。
     * 给定 k的范围是[1, n!]。
     * 示例1:
     * 输入: n = 3, k = 3
     * 输出: "213"
     * 示例2:
     * 输入: n = 4, k = 9
     * 输出: "2314"
     * 链接：https://leetcode-cn.com/problems/permutation-sequence
     * @param n
     * @param k
     * @return
     */

    public static String getPermutation(int n, int k) {
        List<Integer> left = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            left.add(i);
        }
        String result = "";
        int temp = k;
        for (int i = 0; i < n; i ++) {
            if (i == n - 1) {
                result = result + left.get(0);
                break;
            }
            int T = dfs(n - i - 1);
            int index = (temp - 1) / T;
            result = result + left.get(index);
            left.remove(index);
            temp = temp >= T ? temp - T * index : temp;
        }
        return result;
    }

    private static int dfs(int n) {
        if (n <= 1) {
            return n;
        }
        return n * dfs(n - 1);
    }


    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出: 6
     * https://leetcode-cn.com/problems/trapping-rain-water/
     */
    // 按行求
    public static int trap(int[] height) {
        int length = height.length;
        int maxHeight = 0;
        for (int i = 0; i < length; i ++) {
            int h = height[i];
            if (h > maxHeight) {
                maxHeight = h;
            }
        }
        int result = 0;
        for (int j = 0; j < maxHeight; j ++) {
            boolean start = false;

            int w = 0;
            for (int i = 0; i < length; i ++) {
                int h = height[i];
                if (h > 0) {
                    if (start) {
                        result = result + w;
                        w = 0;
                    }
                    start = true;
                    height[i] = h - 1;

                } else {
                    if (start) {
                        w = w + 1;
                    }
                }
            }
        }
        return result;
    }

    // 按列求
    public static int trap1(int[] height) {
        int result = 0;
        int length = height.length;
        for (int i = 0; i < length; i ++) {
            int leftMaxHeight = 0;
            int rightMaxHeight = 0;
            for (int left = i - 1; left >= 0; left --) {
                if (height[left] > leftMaxHeight) {
                    leftMaxHeight = height[left];
                }
            }
            for (int right = i + 1; right <= length - 1; right ++) {
                if (height[right] > rightMaxHeight) {
                    rightMaxHeight = height[right];
                }
            }
            int min = Math.min(leftMaxHeight, rightMaxHeight);
            if (height[i] >= min) {
                continue;
            } else {
                result = result + min - height[i];
            }
        }
        return result;
    }

    public static int trap2(int[] height) {
        int result = 0;
        int length = height.length;
        int[] leftMaxHeight = new int[length];
        int[] rightMaxHeight = new int[length];
        for (int i = 0; i < length; i ++) {
            if (i - 1 >= 0) {
                leftMaxHeight[i] = Math.max(height[i - 1], leftMaxHeight[i - 1]);
            }
        }
        for (int i = length - 1; i >= 0; i --) {
            if (i + 1 <= length - 1) {
                rightMaxHeight[i] = Math.max(height[i + 1], rightMaxHeight[i + 1]);
            }
        }
        for (int i = 0; i < length; i ++) {
            int leftMax = leftMaxHeight[i];
            int rightMax = rightMaxHeight[i];
            int min = Math.min(leftMax, rightMax);
            if (height[i] >= min) {
                continue;
            } else {
                result = result + (min - height[i]);
            }
        }
        return result;
    }

    /**
     * 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
     *
     * 示例1:
     * 输入: [1,2,0]
     * 输出: 3
     *
     * 示例2:
     * 输入: [3,4,-1,1]
     * 输出: 2
     *
     * 示例3:
     * 输入: [7,8,9,11,12]
     * 输出: 1
     * 提示：
     * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的额外空间。
     * 链接：https://leetcode-cn.com/problems/first-missing-positive
     * @param nums
     * @return
     */
    public static int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i ++) {
            int cur = nums[i];
            if (cur <= 0) {
                nums[i] = nums.length + 1;
            }
            if (cur > 0 && cur <= nums.length) {
                nums[i] = -cur;
            }
        }
        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return nums.length + 1;

    }

    public static void main(String[] args) {
        int[] height = new int[] {1,2,0};
        System.out.println(firstMissingPositive(height));
    }

}
