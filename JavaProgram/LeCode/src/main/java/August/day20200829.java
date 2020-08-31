package August;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class day20200829 {
    /**
     * 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
     * 示例1:
     * 输入: "aacecaaa"
     * 输出: "aaacecaaa"
     * 示例 2:
     *
     * 输入: "abcd"
     * 输出: "dcbabcd"
     * 链接：https://leetcode-cn.com/problems/shortest-palindrome
     */
    public static String shortestPalindrome(String s) {
        int length = s.length();
        LinkedList<Character> characters = new LinkedList<>();
        for (int i = 0; i < length; i ++) {
            String subString = s.substring(0, length - i);
            if (check(subString)) {
                break;
            }
            characters.addLast(s.charAt(length - i - 1));
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (!characters.isEmpty()) {
            stringBuilder.append(characters.poll());
        }
        stringBuilder.append(s);
        return stringBuilder.toString();
    }

    private static boolean check(String s) {
        int start = 0;
        int end = s.length() - 1;
        boolean same = true;
        while (start <= end) {
            char startChar = s.charAt(start);
            char endChar = s.charAt(end);
            if (startChar != endChar) {
                same = false;
                break;
            }
            start ++;
            end --;
        }
        return same;
    }

    /**
     * 数字 n代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * 示例：
     *
     * 输入：n = 3
     * 输出：[
     *        "((()))",
     *        "(()())",
     *        "(())()",
     *        "()(())",
     *        "()()()"
     *      ]
     *
     * 链接：https://leetcode-cn.com/problems/generate-parentheses
     * @param
     */
    public static List<String> generateParenthesis(int n) {
        List<Character> lefts = new ArrayList<>();
        List<Character> rights = new ArrayList<>();
        for (int i =0 ; i < n ; i ++) {
            lefts.add('(');
            rights.add(')');
        }
        List<String> results = new ArrayList<>();
        Stack<Character> stack = new Stack<>();

        dfs(lefts, rights, stack, "", results);
        return results;
    }

    private static void dfs(List<Character> lefts, List<Character> rights, Stack<Character> stack, String tempString,
                            List<String> results) {
        if (rights.isEmpty()) {
            results.add(tempString);
            return;
        }

        if (stack.isEmpty()) {
            char left = lefts.remove(0);
            stack.push(left);
            dfs(lefts, rights, stack, tempString + left, results);
            lefts.add(left);
            stack.pop();
        } else {
            if (!lefts.isEmpty()) {
                char left = lefts.remove(0);
                stack.push(left);
                dfs(lefts, rights, stack, tempString + left, results);
                lefts.add(left);
                stack.pop();
            }
            char right = rights.remove(0);
            char top = stack.pop();
            dfs(lefts, rights, stack, tempString + right, results);
            rights.add(right);
            stack.push(top);
        }
    }

    /**
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个位置。
     * 示例1:
     * 输入: [2,3,1,1,4]
     * 输出: true
     * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
     * 示例2:
     *
     * 输入: [3,2,1,0,4]
     * 输出: false
     * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
     * 链接：https://leetcode-cn.com/problems/jump-game
     * @param
     */
    public static boolean canJump(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return true;
        }
        Boolean results[] = new Boolean[length];
        for (int i = 0; i < length; i ++) {
            results[i] = false;
        }
        for (int i = length - 2; i >= 0; i --) {
            int step = nums[i];
            if (step + i >= length - 1) {
                results[i] = true;
            } else {
                for (int j = i + 1; j <= i + step; j ++) {
                    if (results[j]) {
                        results[i] = true;
                        break;
                    }
                }
            }
        }
        return results[0];
    }

    /**
     * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     * 示例1:
     * 输入: coins = [1, 2, 5], amount = 11
     * 输出: 3
     * 解释: 11 = 5 + 5 + 1
     * 示例 2:
     *
     * 输入: coins = [2], amount = 3
     * 输出: -1
     * 链接：https://leetcode-cn.com/problems/coin-change
     * @param
     */
    public static int coinChange(int[] coins, int amount) {
        int[] results = new int[amount + 1];
        for (int i = 1; i <= amount; i ++) {
            results[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < coins.length; i ++) {
            int coin = coins[i];
            for (int j = 0; j <= amount; j ++) {
                if (results[j] != Integer.MAX_VALUE && j + coin <= amount && results[j] + 1 < results[j + coin]) {
                    results[j + coin] = results[j] + 1;
                }
            }
        }
        if (results[amount] == Integer.MAX_VALUE) {
            return -1;
        }
        return results[amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[] { 1,2147483647};
        int amount = 2;
        System.out.println(coinChange(coins, amount));
    }
}
