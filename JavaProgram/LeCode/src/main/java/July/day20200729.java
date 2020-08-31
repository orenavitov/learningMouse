package July;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class day20200729 {

    private static int[] height = new int[]{1,3,2,5,25,24,5};

    /**
     * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     *
     * 说明：你不能倾斜容器，且 n 的值至少为 2。
     * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
     * 示例：
     * 输入：[1,8,6,2,5,4,8,3,7]
     * 输出：49
     *
     * 链接：https://leetcode-cn.com/problems/container-with-most-water
     */
    // O(n^2)
    private static int maxArea(int[] height) {
        int result = 0;
        int length = height.length;
        if (length == 0) {
            return result;
        }
        for (int i = 0; i < length - 1; i ++) {
            for (int j = i + 1; j < length; j ++) {
                int tempHeight = height[i] > height[j] ? height[j] : height[i];
                int tempWidth = j - i;
                int tempResult = tempHeight * tempWidth;
                result = tempResult > result ? tempResult : result;
            }
        }
        return result;
    }

    //O(n)
    private static int maxAreaGeater(int[] height) {
        int result = 0;
        int length = height.length;
        if (length == 0) {
            return result;
        }
        int startIndex = 0;
        int endIndex = length - 1;
        while (startIndex < endIndex) {
            int tempHeight = Math.min(height[startIndex], height[endIndex]);
            int tempWidth = endIndex - startIndex;
            int tempResult = tempHeight * tempWidth;
            result = Math.max(tempResult, result);
            if (startIndex + 1 < endIndex) {
                int leftTempHeight = Math.min(height[startIndex + 1], height[endIndex]);
                int rightTempHeight = Math.min(height[startIndex], height[endIndex - 1]);
                if (leftTempHeight > rightTempHeight) {
                    startIndex ++;
                    continue;
                } else {
                    endIndex --;
                    continue;
                }
//                if (Math.min(height[startIndex + 1], height[endIndex]) > tempHeight) {
//                    startIndex ++;
//                    continue;
//                }
//                if (Math.min(height[endIndex - 1], height[startIndex]) > tempHeight) {
//                    endIndex --;
//                    continue;
//                }
            }
            endIndex --;

        }
        return result;
    }


    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串
     *
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     * @param args
     */
    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        if (s == null) {
            return 0;
        }
        int length = s.length();
        if (length == 0) {
            return 0;
        }
        List<Character> visitedChars = new ArrayList<>();
        int start = 0;
        char[] chars = s.toCharArray();
        while (start < length) {
            int tempStart = start;
            while (tempStart < length) {
                if (!visitedChars.contains(chars[tempStart])) {
                    visitedChars.add(chars[tempStart]);
                    tempStart ++;
                } else {

                    break;
                }
            }
            result = Math.max(visitedChars.size(), result);
            visitedChars.clear();
            start ++;

        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("result : " + lengthOfLongestSubstring(" "));
    }
}
