package August;


import java.util.function.Function;
import java.util.*;

public class day20200810 {
    private static void test() {
        String s = "sfdsfjksjfishfjsfhjdhfjsdhdfdjshgfffdsfs";
        int length = s.length();
        if (length == 0) {
            System.out.println(0);
            return;
        }
        int result = 1;
        int tempResult = 1;
        int preChar = s.charAt(0);
        for (int i = 1; i < length - 1; i ++) {
            char curChar = s.charAt(i);
            if (curChar == preChar) {
                tempResult ++;
            } else {
                result = tempResult > result ? tempResult : result;
                tempResult = 1;
                preChar = curChar;
            }
        }
        System.out.println(result);
    }


    /**
     * 给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
     *
     * 重复出现的子串要计算它们出现的次数。
     *
     * 示例 1 :
     *
     * 输入: "00110011"
     * 输出: 6
     * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
     *
     * 请注意，一些重复出现的子串要计算它们出现的次数。
     *
     * 另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。
     * 示例 2 :
     *
     * 输入: "10101"
     * 输出: 4
     * 解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。
     * 注意：
     *
     * s.length 在1到50,000之间。
     * s 只包含“0”或“1”字符。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/count-binary-substrings
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public static int countBinarySubstrings(String s) {
        int result = 0;
        int length = s.length();
        if (length == 0) {
            return result;
        }
        char preChar = s.charAt(0);
        int repeatTime = 1;
        for (int i = 1; i < length; i ++) {
            char curChar = s.charAt(i);
            if (preChar == curChar) {
                repeatTime ++;

            } else {
                char tempPreChar = curChar;
                int tempRepeatTime = 1;
                if (i + 1 == length) {
                    result ++;
                    break;
                }
                for (int j = i + 1; j < length; j ++) {
                    char tempCurChar = s.charAt(j);
                    if (tempCurChar == tempPreChar) {
                        tempRepeatTime ++;
                        if (j == length - 1) {
                            result = Math.min(tempRepeatTime, repeatTime) + result;
                            break;
                        }
                    } else {
                        result = Math.min(tempRepeatTime, repeatTime) + result;
                        break;
                    }
                }
                preChar = curChar;
                repeatTime = 1;
            }
        }
        return result;
    }

    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     *
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     *
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {


        int length = s.length();
        if (length <= 1) {
            return s;
        }
        String result = "";
        for (int i = 0; i < length - 1; i ++) {
            int end = length - 1;
            while (end > i) {
                String subString = s.substring(i, end + 1);
                if (isPalindrome(subString)) {
                    if (subString.length() > result.length()) {
                        result = subString;
                        break;
                    }
                }
                end --;
            }
        }
        if(result.length() == 0) {
            result = s.substring(0, 1);
        }
        return result;
    }

    private static boolean isPalindrome(String s) {
        int length = s.length();
        int compareTime = length / 2;
        for (int i = 0; i < compareTime; i ++) {
            if (s.charAt(i) != s.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }


    public static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDeitals = firstLine.split(" ");
        int N = Integer.valueOf(firstLineDeitals[0]);
        int allMoney = Integer.valueOf(firstLineDeitals[1]);
        HashMap<Integer, List<Integer>> costItemsMap = new HashMap<>();
        int[] counts = new int[N];
        int[] satifics = new int[N];
        int[] costs = new int[N];
        int allCost = 0;
        for (int i = 0; i < N; i ++) {
            String line = scanner.nextLine();
            String[] lineDetials = line.split(" ");
            int cost = Integer.valueOf(lineDetials[0]);
            int satific = Integer.valueOf(lineDetials[1]);
            int count = Integer.valueOf(lineDetials[2]);
            allCost = cost * count + allCost;
            counts[i] = count;
            satifics[i] = satific;
            costs[i] = cost;
        }
        int[] results = new int[allMoney + 1];
//        for (int i = 1; i < allMoney + 1; i ++) {
//            int resultItem = -1;
//            for(Integer cost : costItemsMap.keySet()) {
//                if (i - cost >= 0) {
//                    for (int item : costItemsMap.get(cost)) {
//                        if (leftCounts[item] > 0) {
//                            int tempResult = satifics[item] + results[i - cost];
//                            if (tempResult > results[i]) {
//                                results[i] = tempResult;
//                                resultItem = item;
//                            }
//                        }
//                    }
//                }
//            }
//            if(resultItem != -1) {
//                int count = leftCounts[resultItem];
//                leftCounts[resultItem] = count - 1;
//            }
//        }
        int[] tempCount = new int[allMoney + 1];
        for (int i = 0; i < N; i ++) {
            int cost = costs[i];
            int count = counts[i];
            int satific = satifics[i];
            for(int j = 0; j < allMoney + 1; j ++) {
                tempCount[j] = count;
            }
            for (int j = 0; j < allMoney + 1 - cost; j ++) {
                if (tempCount[j] == 0) {
                    break;
                }
                int preSatific = results[j + cost];
                if (results[j] + satific > preSatific && tempCount[j] > 0) {
                    results[j + cost] = results[j] + satific;
                    int preLeftCount = tempCount[j];
                    tempCount[j + cost] = preLeftCount - 1;
                }
            }
//            for (int j = 1; j <= count;j ++) {
//                int increaseCost = j * cost;
//                int preSatific = results[increaseCost];
//                if (j * satific > preSatific) {
//                    results[increaseCost] = j * satific;
//                }
//            }
        }
        int result = 0;
        if (allCost > allMoney) {
            result = results[allMoney];
        } else {
            result = results[allCost];
        }

        System.out.println(result);
    }

    public static void main(String[] args) {
        Solution();
    }
}
