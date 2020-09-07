package Sep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * n皇后问题研究的是如何将 n个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 上图为 8 皇后问题的一种解法。
 * 给定一个整数 n，返回所有不同的n皇后问题的解决方案。
 * 每一种解法包含一个明确的n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * 示例：
 *
 * 输入：4
 * 输出：[
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 * 提示：
 * 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
 * 链接：https://leetcode-cn.com/problems/n-queens
 */
public class day20200903 {

    private static int N = 0;

    private static List<List<String>> solveNQueens(int n) {
        N = n;
        List<List<String>> results = new ArrayList<>();
        dfs(0, new ArrayList<>(), new ArrayList<>(), results);
        return results;
    }

    private static void dfs(int row, List<Integer> cols, List<String> pre, List<List<String>> result) {
        StringBuilder stringBuilder = new StringBuilder();

        if (row == N - 1) {
            for (int i = 0; i < N; i ++) {
                if (check(cols, row, i)) {
                    String temp = genString(N, i);
                    pre.add(temp);
                    result.add(pre);
                }
            }
            return;
        }
        for (int i = 0; i < N; i ++) {
            if (check(cols, row, i)) {
                String temp = genString(N, i);
                cols.add(i);
                pre.add(temp);
                dfs(row + 1, cols, new ArrayList<>(pre), result);
                cols.remove(cols.size() - 1);
                pre.remove(pre.size() - 1);
            }
        }
    }

    private static boolean check(List<Integer> cols, int row, int col) {

            for (int j = 0; j < cols.size(); j ++) {
                if (col == cols.get(j) || Math.abs(row - j) == Math.abs(col - cols.get(j))) {
                    return false;
                }
            }



        return true;
    }

    private static String genString(int n, int col) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i ++) {
            if (i == col) {
                stringBuilder.append('Q');
                continue;
            }
            stringBuilder.append('.');
        }
        return stringBuilder.toString();
    }

    /**
     * 输入一个字符串，打印出该字符串中字符的所有排列。
     * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
     * 示例:
     *
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param s
     */
    public static String[] permutation(String s) {
        List<String> results = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        for (int i = 0; i < s.length(); i ++) {
            characterList.add(s.charAt(i));
        }
        dfs(0, s.length(), characterList, "", results);
        String[] arrayResult = new String[results.size()];
        for (int i = 0; i < results.size(); i ++) {
            arrayResult[i] = results.get(i);
        }
        return arrayResult;
    }


    private static void dfs(int curIndex, int length, List<Character> unusedChars,
                            String tempString, List<String> results) {
        if (curIndex == length - 1) {
            results.add(tempString + unusedChars.get(0));
            return;
        }
        List<Character> curIndexChars = new ArrayList<>();
        for(int i = 0; i < unusedChars.size();i ++) {
            char curChar = unusedChars.get(i);
            if (!curIndexChars.contains(curChar)) {
                curIndexChars.add(curChar);
                unusedChars.remove(i);
                tempString = tempString + curChar;
                dfs(curIndex + 1, length, unusedChars, tempString, results);
                tempString = tempString.substring(0, tempString.length() - 1);
                unusedChars.add(i, curChar);

            }
        }

    }

    public static void main(String[] args) {
//        List<List<String>> results = solveNQueens(8);
//        results.forEach(result -> {
//            System.out.println(result.toString());
//        });
        String s = "baiducdn";
        String[] results = permutation(s);
        System.out.println(results.length);
        for (int i = 0; i < results.length; i ++) {
            System.out.print(results[i] + " ");
        }
    }
}
