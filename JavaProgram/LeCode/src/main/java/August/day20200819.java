package August;

public class day20200819 {
    /**
     * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
     *
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     *
     *  
     *
     * 示例 1：
     *
     * 输入："abc"
     * 输出：3
     * 解释：三个回文子串: "a", "b", "c"
     * 示例 2：
     *
     * 输入："aaa"
     * 输出：6
     * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
     *
     * 链接：https://leetcode-cn.com/problems/palindromic-substrings
     * @param s
     * @return
     */
    public static int countSubstrings(String s) {
        int result = 0;
        int length = s.length();
        int centerLeft = 0;
        while (centerLeft < length) {

            int centerRight = centerLeft;
            for (int i = 0; i < 2; i ++) {
                centerRight = centerRight + i;
                for (int d = 0; d < length; d ++) {
                    int left = centerLeft - d;
                    int right = centerRight + d;
                    if (left >= 0 && right <= length - 1) {
                        char leftChar = s.charAt(left);
                        char rightChar = s.charAt(right);
                        if (leftChar == rightChar) {
                            result ++;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }

            centerLeft ++;
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "aaa";
        System.out.println("result : " + countSubstrings(s));
    }
}
