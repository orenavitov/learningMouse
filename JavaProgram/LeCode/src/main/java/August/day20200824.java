package August;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class day20200824 {
    /**
     * 给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，返回此范围内所有数字的按位与（包含 m, n 两端点）。
     * <p>
     * 示例 1: 
     * <p>
     * 输入: [5,7]
     * 输出: 4
     * 示例 2:
     * <p>
     * 输入: [0,1]
     * 输出: 0
     * <p>
     * 链接：https://leetcode-cn.com/problems/bitwise-and-of-numbers-range
     *
     * @param m
     * @param n
     * @return
     */
    public static int rangeBitwiseAnd(int m, int n) {
        int remove = 0;
        while (m < n) {
            m = m >> 1;
            n = n >> 1;
            remove++;
        }
        return m << remove;
    }


    /**
     * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
     * <p>
     * 示例 1:
     * <p>
     * 输入: "abab"
     * <p>
     * 输出: True
     * <p>
     * 解释: 可由子字符串 "ab" 重复两次构成。
     * 示例 2:
     * <p>
     * 输入: "aba"
     * <p>
     * 输出: False
     * 示例 3:
     * <p>
     * 输入: "abcabcabcabc"
     * <p>
     * 输出: True
     * <p>
     * 解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)
     * <p>
     * 链接：https://leetcode-cn.com/problems/repeated-substring-pattern
     *
     * @param
     */
    public static boolean repeatedSubstringPattern(String s) {
        int length = s.length();
        if (length < 2) {
            return false;
        }
        for (int step = 1; step <= length / 2; step++) {
            if (length % step == 0) {
                boolean tempResult = true;
                for (int start = 0; start < step; start++) {
                    char curChar = s.charAt(start);
                    for (int time = 0; time < length / step; time++) {
                        int next = start + time * step;
                        if (s.charAt(next) != curChar) {
                            tempResult = false;
                            break;
                        }
                    }
                    if (!tempResult) {
                        break;
                    }
                }
                if (tempResult) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
     * <p>
     * 注意:
     * <p>
     * num 的长度小于 10002 且 ≥ k。
     * num 不会包含任何前导零。
     * 示例 1 :
     * <p>
     * 输入: num = "1432219", k = 3
     * 输出: "1219"
     * 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
     * 示例 2 :
     * <p>
     * 输入: num = "10200", k = 1
     * 输出: "200"
     * 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
     * 示例 3 :
     * <p>
     * 输入: num = "10", k = 2
     * 输出: "0"
     * 解释: 从原数字移除所有的数字，剩余为空就是0。
     *
     * @param
     */
    public static String removeKdigits(String num, int k) {
        int length = num.length();
        if (length == k) {
            return "0";
        }
        int keepLength = length - k;
        LinkedList<Character> characters = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            char curChar = num.charAt(i);

            while (!characters.isEmpty() && characters.getLast() > curChar && k > 0) {
                characters.removeLast();

                k--;
            }
            characters.addLast(curChar);


        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < keepLength; i++) {
            result.append(characters.poll());
        }
        String s = result.toString();
        int startIndex = 0;
        while (startIndex < keepLength) {
            if (s.charAt(startIndex) != '0') {
                break;
            }
            startIndex++;
        }
        return s.substring(startIndex, keepLength);
    }

    /**
     * 给定一个整数数组 nums ，你可以对它进行一些操作。
     *
     * 每次操作中，选择任意一个 nums[i] ，删除它并获得 nums[i] 的点数。之后，你必须删除每个等于 nums[i] - 1 或 nums[i] + 1 的元素。
     *
     * 开始你拥有 0 个点数。返回你能通过这些操作获得的最大点数。
     *
     * 示例 1:
     *
     * 输入: nums = [3, 4, 2]
     * 输出: 6
     * 解释:
     * 删除 4 来获得 4 个点数，因此 3 也被删除。
     * 之后，删除 2 来获得 2 个点数。总共获得 6 个点数。
     * 示例 2:
     *
     * 输入: nums = [2, 2, 3, 3, 3, 4]
     * 输出: 9
     * 解释:
     * 删除 3 来获得 3 个点数，接着要删除两个 2 和 4 。
     * 之后，再次删除 3 获得 3 个点数，再次删除 3 获得 3 个点数。
     * 总共获得 9 个点数。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/delete-and-earn
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param args
     */

    /**
     * 返回字符串 text 中按字典序排列最小的子序列，该子序列包含 text 中所有不同字符一次。
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入："cdadabcc"
     * 输出："adbc"
     * 示例 2：
     * <p>
     * 输入："abcd"
     * 输出："abcd"
     * 示例 3：
     * <p>
     * 输入："ecbacba"
     * 输出："eacb"
     * 示例 4：
     * <p>
     * 输入："leetcode"
     * 输出："letcod"
     * <p>
     * 链接：https://leetcode-cn.com/problems/smallest-subsequence-of-distinct-characters
     *
     * @param
     */
    public static String smallestSubsequence(String text) {
        int length = text.length();
        LinkedList<Character> characters = new LinkedList<>();
        HashMap<Character, Integer> charRemoveTime = new HashMap<>();
        for (int i = 0; i < length; i++) {
            char curChar = text.charAt(i);
            if (!charRemoveTime.containsKey(curChar)) {
                charRemoveTime.put(curChar, 1);
            } else {
                int leftTime = charRemoveTime.get(curChar);
                charRemoveTime.put(curChar, leftTime + 1);
            }
        }
        for (int i = 0; i < length; i++) {
            char curChar = text.charAt(i);


            while (!characters.isEmpty() && characters.getLast() > curChar) {
                char topChar = characters.getLast();
                int leftTime = charRemoveTime.get(topChar);
                if (leftTime > 1) {
                    characters.removeLast();
                    charRemoveTime.put(topChar, leftTime - 1);
                } else {
                    break;
                }
            }

            if (!characters.contains(curChar)) {
                characters.addLast(curChar);
            } else {
                charRemoveTime.put(curChar, charRemoveTime.get(curChar) - 1);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!characters.isEmpty()) {
            result.append(characters.removeFirst());
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(smallestSubsequence("abcd"));
    }
}
