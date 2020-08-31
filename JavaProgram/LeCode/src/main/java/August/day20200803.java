package August;

import java.util.stream.IntStream;

public class day20200803 {
    /**
     * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
     *
     * 注意：
     *
     * num1 和num2 的长度都小于 5100.
     * num1 和num2 都只包含数字 0-9.
     * num1 和num2 都不包含任何前导零。
     * 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
     * 链接：https://leetcode-cn.com/problems/add-strings
     */
    public static String addStrings(String num1, String num2) {
        char[] chars1 = null;
        char[] chars2 = null;
        int N1 = num1.length();
        int N2 = num2.length();
        if (N1 > N2) {
            chars1 = num1.toCharArray();
            chars2 = num2.toCharArray();
        } else {
            chars1 = num2.toCharArray();
            chars2 = num1.toCharArray();
        }
        boolean needAdd = false;
        int Nmax = Math.max(N1, N2);
        int Nmin = Math.min(N1, N2);
        char[] result = new char[Nmax];
        for (int i = 0; i < Nmax; i ++) {
            int subNum1 = chars1[Nmax - 1 - i] - 48;
            int subNum2 = i < Nmin ? chars2[Nmin - 1 - i] - 48 : 0;
            int subNum = needAdd ? subNum1 + subNum2 + 1 : subNum1 + subNum2;
            if (subNum >= 10) {
                result[Nmax - 1 - i] = (char) (subNum - 10 + 48);
                needAdd = true;
            } else {
                result[Nmax - 1 - i] = (char) (subNum + 48);
                needAdd = false;
            }
        }

        if (needAdd) {
            return "1" + String.valueOf(result);
        } else {
            return String.valueOf(result);
        }

    }


    public static void main(String[] args) {
        System.out.println(addStrings("9",
                "99"));
    }
}
