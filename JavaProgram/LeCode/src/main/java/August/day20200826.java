package August;

import java.util.*;

public class day20200826 {


    public static List<String> letterCombinations(String digits) {
        HashMap<Character, List<String>> integerStringHashMap = new HashMap<>();
        List<String> results = new ArrayList<>();
        integerStringHashMap.put('2', Arrays.asList("a", "b", "c"));
        integerStringHashMap.put('3', Arrays.asList("d", "e", "f"));
        integerStringHashMap.put('4', Arrays.asList("g", "h", "i"));
        integerStringHashMap.put('5', Arrays.asList("j", "k", "l"));
        integerStringHashMap.put('6', Arrays.asList("m", "n", "o"));
        integerStringHashMap.put('7', Arrays.asList("p", "q", "r", "s"));
        integerStringHashMap.put('8', Arrays.asList("t", "u", "v"));
        integerStringHashMap.put('9', Arrays.asList("w", "x", "y", "z"));
        int length = digits.length();
        if (length == 0) {
            return results;
        }
        if (length == 1) {
            char curChar = digits.charAt(0);
            return integerStringHashMap.get(curChar);
        }
        dfs(digits, length, 0, new ArrayList<>(), integerStringHashMap, results);
        return results;
    }

    public static void dfs(String digits, int length, int curIndex, List<String> tempReult, HashMap<Character,
            List<String>> integerStringHashMap, List<String> results) {

        char curChar = digits.charAt(curIndex);
        List<String> strings = integerStringHashMap.get(curChar);
        if (tempReult.isEmpty()) {
            tempReult.addAll(strings);
            dfs(digits, length, curIndex + 1, tempReult, integerStringHashMap, results);
        } else {
            if (curIndex == length - 1) {
                for (String s : strings) {
                    for (String temp : tempReult) {
                        results.add(temp.concat(s));
                    }
                }
            } else {
                List<String> newTempResults = new ArrayList<>();
                for(String s : strings) {
                    for (String temp : tempReult) {
                        newTempResults.add(temp.concat(s));
                    }
                }
                tempReult = null;
                dfs(digits, length, curIndex + 1, newTempResults, integerStringHashMap, results);
            }


        }
    }

    /**
     * 给定一个字符串S，计算S的不同非空子序列的个数。
     *
     * 因为结果可能很大，所以返回答案模 10^9 + 7.

     * 示例 1：
     * 输入："abc"
     * 输出：7
     * 解释：7 个不同的子序列分别是 "a", "b", "c", "ab", "ac", "bc", 以及 "abc"。
     * 示例 2：
     * 输入："aba"
     * 输出：6
     * 解释：6 个不同的子序列分别是 "a", "b", "ab", "ba", "aa" 以及 "aba"。
     * 示例 3：
     * 输入："aaa"
     * 输出：3
     * 解释：3 个不同的子序列分别是 "a", "aa" 以及 "aaa"。
     * 链接：https://leetcode-cn.com/problems/distinct-subsequences-ii
     * @param S
     */
    public static int distinctSubseqII(String S) {
        //TODO
        return 0;
    }

    /**
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     *
     * 示例:
     *
     * 输入: [10,9,2,5,3,7,101,18]
     * 输出: 4
     * 解释: 最长的上升子序列是[2,3,7,101]，它的长度是 4。
     * 说明:
     *
     * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
     * 你算法的时间复杂度应该为O(n2) 。
     * 进阶: 你能将算法的时间复杂度降低到O(n log n) 吗?
     *
     * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
     * @param
     */
    public static int lengthOfLIS(int[] nums) {
        int length = nums.length;
        if (length <= 1) {
            return length;
        }
        int[] results = new int[length];
        for (int i = 0; i < length; i ++) {
            results[i] = 1;
        }
        for (int i = 1; i < length; i ++) {
            int cur = nums[i];
            int tempLongest = 1;
            for (int j = i - 1; j >= 0; j --) {
                if (cur > nums[j]) {
                    if (results[j] + 1 > tempLongest) {
                        results[i] = results[j] + 1;
                        tempLongest = results[j] + 1;
                    }

                }
            }
        }
        int longest = 1;
        for (int i = 0; i < length; i ++) {
            if (results[i] > longest) {
                longest = results[i];
            }
        }
        return longest;
    }

    /**
     * 给定一个整数n, 返回从1到n的字典顺序。
     *
     * 例如，
     *
     * 给定 n =1 3，返回 [1,10,11,12,13,2,3,4,5,6,7,8,9] 。
     *
     * 请尽可能的优化算法的时间复杂度和空间复杂度。 输入的数据n小于等于5,000,000。
     *
     * 链接：https://leetcode-cn.com/problems/lexicographical-numbers
     * 另一个：http://www.bubuko.com/infodetail-1719034.html
     * @param n
     */
    private static List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < 10; i ++) {
            if (i <= n) {
                result.add(i);
                dfs(i, result, n);
            }

        }
        return result;
    }

    private static void dfs(int rootNum, List<Integer> result, int n) {
        if (rootNum > n) {
            return;
        }
        int start = rootNum * 10;
        for (int i = 0; i <= 9; i ++) {
            if (start + i <= n) {
                result.add(start + i);
                dfs(start + i, result, n);
            }
        }
    }

    /**
     * 给定整数n和k，找到1到n中字典序第k小的数字。
     *
     * 注意：1 ≤ k ≤ n ≤ 109。
     * 示例 :
     * 输入:
     * n: 13   k: 2
     * 输出:
     * 10
     * 解释:
     * 字典序的排列是 [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]，所以第二小的数字是 10。
     * 链接：https://leetcode-cn.com/problems/k-th-smallest-in-lexicographical-order
     * @param args
     */


    /**
     * 输入一个字符串，打印出该字符串中字符的所有排列。
     * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
     * 示例:
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     * 链接：https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof
     * @param
     */


    /**
     * 给你一个字符串s，找出它的所有子串并按字典序排列，返回排在最后的那个子串。
     * 示例 1：
     * 输入："abab"
     * 输出："bab"
     * 解释：我们可以找出 7 个子串 ["a", "ab", "aba", "abab", "b", "ba", "bab"]。按字典序排在最后的子串是 "bab"。
     * 示例2：
     *
     * 输入："leetcode"
     * 输出："tcode"
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/last-substring-in-lexicographical-order
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param
     */
    private static String test(String s) {

        int length = s.length();
        if (length <= 1) {
            return s;
        }
        TreeMap<Character, List<Integer>> charAndIndexes = new TreeMap<>();
        for (int i = 0; i < length; i ++) {
            char cur = s.charAt(i);
            if (!charAndIndexes.containsKey(cur)) {
                charAndIndexes.put(cur, new ArrayList<>());
            }
            charAndIndexes.get(cur).add(i);
        }
        Map.Entry<Character, List<Integer>> characterListEntry = charAndIndexes.pollFirstEntry();
        while (characterListEntry != null) {
            List<Integer> indexes = characterListEntry.getValue();



            characterListEntry = charAndIndexes.pollFirstEntry();
        }

        return null;
    }

    private static List<Integer> sortIndex(List<Integer> indexes, String s) {
        int length = s.length();
        for (int i = 1; i < length; i ++) {

            for (int index : indexes) {

            }
        }
        return null;
    }


    public static void main(String[] args) {
        List<Integer> result = lexicalOrder(100);
        result.forEach(i -> {
            System.out.print(i + " ");
        });
    }
}
