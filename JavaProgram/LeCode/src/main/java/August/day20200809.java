package August;

import java.util.ArrayList;
import java.util.List;

public class day20200809 {
    /**
     * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
     *
     * 有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
     *
     *  
     *
     * 示例:
     *
     * 输入: "25525511135"
     * 输出: ["255.255.11.135", "255.255.111.35"]
     *
     * 链接：https://leetcode-cn.com/problems/restore-ip-addresses
     */
    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        int length = s.length();
        for (int i = 0; i < 3; i ++) {
            for (int j = i + 1; j < i + 4; j ++) {

                for (int k = j + 1; k < length - 1 && k < j + 4; k ++) {
                    if (j + 7 < length) {
                        continue;
                    }
                    String subString1 = s.substring(0, i + 1);
                    String subString2 = s.substring(i + 1, j + 1);
                    String subString3 = s.substring(j + 1, k + 1);
                    String subString4 = s.substring(k + 1, length);
                    if ((subString1.length() > 1 && subString1.charAt(0) == '0') ||
                            (subString2.length() > 1 && subString2.charAt(0) == '0') ||
                            (subString3.length() > 1 && subString3.charAt(0) == '0') ||
                            (subString4.length() > 1 && subString4.charAt(0) == '0')) {
                        continue;
                    }
                    if (Integer.valueOf(subString1) <= 255 &&
                        Integer.valueOf(subString2) <= 255 &&
                        Integer.valueOf(subString3) <= 255 &&
                        Integer.valueOf(subString4) <= 255) {
                        StringBuilder stringBuilder = new StringBuilder();
                        result.add(stringBuilder.append(subString1).append(".")
                                                .append(subString2).append(".")
                                                .append(subString3).append(".")
                                                .append(subString4).toString());
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "0000";
        List<String> result = restoreIpAddresses(s);
        result.forEach(System.out::println);
    }
}
