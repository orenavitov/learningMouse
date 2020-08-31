package August;

import java.util.*;

public class day20200827 {
    /**
     * 给定一个机票的字符串二维数组 [from, to]，子数组中的两个成员分别表示飞机出发和降落的机场地点，对该行程进行重新规划排序。所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
     *
     * 说明:
     *
     * 如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前
     * 所有的机场都用三个大写字母表示（机场代码）。
     * 假定所有机票至少存在一种合理的行程。
     * 示例 1:
     *
     * 输入: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
     * 输出: ["JFK", "MUC", "LHR", "SFO", "SJC"]
     * 示例 2:
     *
     * 输入: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
     * 输出: ["JFK","ATL","JFK","SFO","ATL","SFO"]
     * 解释: 另一种有效的行程是["JFK","SFO","ATL","JFK","ATL","SFO"]。但是它自然排序更大更靠后。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reconstruct-itinerary
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static List<String> findItinerary(List<List<String>> tickets) {
        List<String> result = new ArrayList<>();
        HashMap<String, List<String>> srcDstsMap = new HashMap<>();
        for (List<String> ticket : tickets) {
            String src = ticket.get(0);
            String dst = ticket.get(1);
            srcDstsMap.computeIfAbsent(src, d -> {
                return new ArrayList<String>();
            });
            srcDstsMap.computeIfPresent(src, (k, v) -> {
                v.add(dst);
                return v;
            });
        }
        for (String s : srcDstsMap.keySet()) {
            srcDstsMap.get(s).sort((s1, s2) -> {
                int length1 = s1.length();
                int length2 = s2.length();
                int min = Math.min(length1, length2);
                for (int i = 0; i < min; i ++) {
                    char c1 = s1.charAt(i);
                    char c2 = s2.charAt(i);
                    if (c1 != c2) {
                        return (int) c1 - (int) c2;
                    }
                }
                return length1 - length2;
            });
        }
        String cur = "JFK";
        dfs(srcDstsMap, cur, result);
        Collections.reverse(result);
        return result;
    }

    private static void dfs(HashMap<String, List<String>> srcDstsMap, String curNode, List<String> vistied) {
        List<String> dests = srcDstsMap.get(curNode);
        while (dests != null && dests.size() > 0) {
            String dst = dests.remove(0);
            dfs(srcDstsMap, dst, vistied);
        }
        vistied.add(curNode);

    }

    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 示例 1：
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
     * @param s
     */
    public static String longestPalindrome(String s) {
        int length = s.length();
        if (length <= 1) {
            return s;
        }
        Boolean[][] result = new Boolean[length][length];
        for (int i = 0; i < length; i ++) {
            for (int j = 0; j < length; j ++) {
                result[i][j] = false;
            }
        }
        String resultString = "";
        for (int l = 0; l < length; l ++) {
            for (int index = 0; index + l < length; index ++) {
                if (l == 0) {
                    result[index][l] = true;
                }
                if (l == 1) {
                    result[index][l] = s.charAt(index) == s.charAt(index + l);
                }
                if (l > 1) {
                    result[index][l] = result[index + 1][l -2] && (s.charAt(index) == s.charAt(index + l));
                }
                if (result[index][l] && l + 1 > resultString.length()) {
                    resultString = s.substring(index, index + l + 1);
                }
            }
        }

        return resultString;
    }

    /**
     * 给定一个字符串str，返回把str全部切成回文子串的最小分割数。
     * 例如：
     * str = “ABA”，str本身就是回文串，返回0.
     * str = “ACDCDCDAD”，最少需要切两次变成3个回文子串，所以返回2.
     */
    private static int test(String s) {
        int length = s.length();
        if (length == 0) {
            return 0;
        }
        Boolean[][] result = new Boolean[length][length];
        for (int i = 0; i < length; i ++) {
            for (int j = 0; j < length; j ++) {
                result[i][j] = false;
            }
        }
        String resultString = "";
        for (int l = 0; l < length; l ++) {
            for (int index = 0; index + l < length; index ++) {
                if (l == 0) {
                    result[index][l] = true;
                }
                if (l == 1) {
                    result[index][l] = s.charAt(index) == s.charAt(index + l);
                }
                if (l > 1) {
                    result[index][l] = result[index + 1][l -2] && (s.charAt(index) == s.charAt(index + l));
                }
                if (result[index][l] && l + 1 > resultString.length()) {
                    resultString = s.substring(index, index + l + 1);
                }
            }
        }
        int[] devides = new int[length + 1];
        for (int i = 0; i < length + 1; i ++) {
            devides[i] = i;
        }
        devides[0] = 0;
        for (int i = 1; i <= length; i ++) {
            if (result[0][i - 1]) {
                devides[i] = 1;
                continue;
            }
            for(int j = i - 1; j >= 1; j --) {
                if (result[j][i - j - 1] && devides[i] > devides[j] + 1) {
                    devides[i] = devides[j] + 1;
                }
            }
        }
        return devides[length] - 1;
    }


    /**
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     *
     * 示例:
     *
     * 输入: [1,2,3]
     * 输出:
     * [
     *   [1,2,3],
     *   [1,3,2],
     *   [2,1,3],
     *   [2,3,1],
     *   [3,1,2],
     *   [3,2,1]
     * ]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/permutations
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        ArrayList<Integer> leftNums = new ArrayList<>();
        for (int i = 0; i < nums.length; i ++) {
            leftNums.add(nums[i]);
        }
        getSubList(leftNums, new ArrayList<Integer>(), results);
        return results;
    }

    private static void getSubList(ArrayList<Integer> leftNums, List<Integer> visitedNums, List<List<Integer>> results) {
        int leftLength = leftNums.size();
        if (leftLength == 0) {
            results.add(visitedNums);
            return;
        }
        for (int i = 0; i < leftLength; i ++) {
            ArrayList<Integer> newVisitedNums = new ArrayList<>(visitedNums);
            int num = leftNums.remove(i);

            newVisitedNums.add(num);
            getSubList(leftNums, newVisitedNums, results);
            // 回溯， 撤销操作
            leftNums.add(i, num);
        }
    }

    public static void main(String[] args) {
//        int[] nums = new int[] {1,2,3};
//        List<List<Integer>> results = permute(nums);
//        results.forEach(result -> {
//            result.forEach(i -> {
//                System.out.print(i + " ");
//            });
//            System.out.print("\n");
//        });
        System.out.println(test("abcbded"));
    }
}
