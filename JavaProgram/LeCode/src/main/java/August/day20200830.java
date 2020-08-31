package August;

import java.util.Stack;

public class day20200830 {
    /**
     * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * 示例：
     * 输入："Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     * 链接：https://leetcode-cn.com/problems/reverse-words-in-a-string-iii
     */
    public static String reverseWords(String s) {
        StringBuilder result  = new StringBuilder();
        int length = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < length; i ++) {
            if (s.charAt(i) == ' ') {
                while (!stack.isEmpty()) {
                    result.append(stack.pop());
                }
                result.append(" ");
            } else {
                stack.push(s.charAt(i));
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行Z 字形排列。
     *
     * 比如输入字符串为 "LEETCODEISHIRING"行数为 3 时，排列如下：
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
     * 请你实现这个将字符串进行指定行数变换的函数：
     *
     * string convert(string s, int numRows);
     * 示例1:
     *
     * 输入: s = "LEETCODEISHIRING", numRows = 3
     * 输出: "LCIRETOESIIGEDHN"
     * 示例2:
     *
     * 输入: s = "LEETCODEISHIRING", numRows =4
     * 输出:"LDREOEIIECIHNTSG"
     * 解释:
     * L     D     R
     * E   O E   I I
     * E C   I H   N
     * T     S     G
     * 链接：https://leetcode-cn.com/problems/zigzag-conversion
     * @param
     */
    public static String convert(String s, int numRows) {
        int length = s.length();
        if (numRows == 1) {
            return s;
        }
        int T = 2 * numRows - 2;
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < numRows; row ++) {
            int start = row;
            int end = T - start;
            while (start < length) {
                result.append(s.charAt(start));

                if (end - start < T && end -start > 0 && end < length) {
                    result.append(s.charAt(end));
                }

                start = start + T;
                end = end + T;
            }
        }
        return result.toString();
    }

    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        String s = "AB";
        int numRows = 1;
        System.out.println(convert(s, numRows));
    }
}
