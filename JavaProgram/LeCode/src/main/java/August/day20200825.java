package August;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class day20200825 {
    /**
     * 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
     *
     * 示例:
     *
     * 输入: [4, 6, 7, 7]
     * 输出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
     * 说明:
     *
     * 给定数组的长度不会超过15。
     * 数组中的整数范围是 [-100,100]。
     * 给定数组中可能包含重复数字，相等的数字应该被视为递增的一种情况
     *
     * 链接：https://leetcode-cn.com/problems/increasing-subsequences
     */
    private static int maxLength = 0;

    private static List<List<Integer>> result = new ArrayList<>();

    private static List<Integer> visited = new ArrayList<>();
    public static List<List<Integer>> findSubsequences(int[] nums) {
        maxLength = nums.length;
        List<Integer> sub1 = new ArrayList<>();
        List<Integer> sub2 = new ArrayList<>();
        visit(0, nums, sub1);
        unvisit(0, nums, sub2);
        return result;
    }

    private static void visit(int curIndex, int[] nums, List<Integer> sub) {
        if (curIndex == maxLength) {
            if (sub.size() > 1) {
                int value = getValue(sub);
                if (!visited.contains(value)) {
                    visited.add(value);
                    result.add(sub);
                }
            }
            return;
        }
        if (curIndex == 0) {
            sub.add(nums[curIndex]);
            visit(curIndex + 1, nums, new ArrayList<>(sub));
            unvisit(curIndex + 1, nums, new ArrayList<>(sub));
        } else {
            if (nums[curIndex] >= nums[curIndex - 1]) {
                sub.add(nums[curIndex]);
                visit(curIndex + 1, nums, new ArrayList<>(sub));
                unvisit(curIndex + 1, nums, new ArrayList<>(sub));
            }
        }
        sub = null;
    }

    private static void unvisit(int curIndex, int[] nums, List<Integer> sub) {
        if (curIndex == maxLength) {
            if (sub.size() > 1) {
                int value = getValue(sub);
                if (!visited.contains(value)) {
                    visited.add(value);
                    result.add(sub);
                }
            }

            return;
        }
        visit(curIndex + 1, nums, new ArrayList<>(sub));
        unvisit(curIndex + 1, nums, new ArrayList<>(sub));
        sub = null;
    }

    private static int getValue(List<Integer> sub) {
        int value = 0;
        int length = sub.size();
        if (length == 0) {
            return value;
        } else {
            for (int i = 0; i < length; i ++) {
                value = value + (int)(Math.pow(10, i) * sub.get(i));
            }
        }
        return value;
    }

    /**
     * 硬币。给定数量不限的硬币，币值为25分、10分、5分和1分，编写代码计算n分有几种表示法。(结果可能会很大，你需要将结果模上1000000007)
     *
     * 示例1:
     *
     *  输入: n = 5
     *  输出：2
     *  解释: 有两种方式可以凑成总金额:
     * 5=5
     * 5=1+1+1+1+1
     * 示例2:
     *
     *  输入: n = 10
     *  输出：4
     *  解释: 有四种方式可以凑成总金额:
     * 10=10
     * 10=5+5
     * 10=5+1+1+1+1+1
     * 10=1+1+1+1+1+1+1+1+1+1
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/coin-lcci
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param
     */

    public static int waysToChange(int n) {
        int[] results = new int[n + 1];
        List<Integer> moneyType = new ArrayList<>();
        moneyType.add(1);
        moneyType.add(5);
        moneyType.add(10);
        moneyType.add(25);
        results[0] = 1;
        results[1] = 0;
        for (int money : moneyType) {
            for (int i = 0; i <= n - money; i ++) {

                    results[i + money] = (results[i + money] + results[i]) % 1000000007;

            }
        }

        return results[n];
    }

    /**
     * 01背包问题， 每个物品只有一件
     * @return
     */
    private static int packageProblem1() {
        int MAX = 20;
        int[] values = new int[]{3, 5, 7, 9, 10};
        int[] weights = new int[]{3, 5, 7, 8, 9};
        int[] result = new int[MAX + 1];
        for (int i = 0; i < 5; i++) {
            int curWeight = weights[i];
            for (int j = MAX; j >= curWeight; j--) {
                int pre = j - curWeight;
                result[j] = Math.max(result[j], result[pre] + values[i]);
            }
        }

        return result[MAX];
    }

    /**
     * 完全背包问题， 每个物品有无数件；
     * @return
     */
    private static int packageProblem2() {
        int MAX = 20;
        int[] values = new int[]{3, 5, 7, 9, 10};
        int[] weights = new int[]{3, 5, 7, 8, 9};
        int[] result = new int[MAX + 1];
        for (int i = 0; i < 5; i++) {
            int curWeight = weights[i];
            for (int j = 0; j <= MAX - curWeight; j ++) {
                result[j + curWeight] = Math.max(result[j + curWeight], result[j] + values[i]);
            }
        }

        return result[MAX];
    }

    /**
     * 多重背包问题， 每个物品的数量是有限的
     * @param
     */
    private static int packageProblem3() {
        int MAX = 20;
        int[] values = new int[]{3, 5, 7, 9, 10};
        int[] weights = new int[]{3, 5, 7, 8, 9};
        int[] counts = new int[] {3, 2, 1, 4, 5};
        int[] result = new int[MAX + 1];
        for (int i = 0; i < 5; i++) {
            int curWeight = weights[i];
            int count = counts[i];
            for (int k = 1; k <= count; k ++) {
                for (int j = MAX; j >= curWeight * k && j - k * curWeight >= 0; j --) {
                    result[j] = Math.max(result[j], result[j - curWeight] + values[i]);
                }
            }

        }

        return result[MAX];
    }

    /**
     * 分组背包问题：
     * 第一行输入两个整数m, n, 其中m表示英雄数量， n表示可以每个英雄可以装备的最多装备数量；
     * 接下来输入m行， 第i行表示第i个英雄装备1件、2件、.....n件时的战斗力；
     * 请你合理的分配装备，使得m个英雄的战斗力总和最多
     * @return
     */
    private static int packageProblem4() {
        int m = 3;
        int n = 3;
        int[][] powers = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {2, 6, 6}
        };
        int[] results = new int[n + 1];
        for (int i = 0; i < m; i ++) {
            int[] heroPowers = powers[i];
            for (int j = n; j >= 0; j --) {

                for (int k = 1; k <= n && j - k >= 0; k ++) {
                    int power = heroPowers[k - 1];
                    results[j] = Math.max(results[j - k] + power, results[j]);
                }
            }
        }
        return results[n];
    }

    public static void main(String[] args) {
        System.out.println(packageProblem4());

    }
}
