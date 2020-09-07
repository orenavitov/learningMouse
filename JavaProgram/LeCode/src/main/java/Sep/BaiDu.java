package Sep;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BaiDu {

    private static int max = -1;

    /**
     * 性质1：如果数a、b都能被c整除，那么它们的和（a+b）或差(a－b)也能被c整除。
     *
     * 性质2：几个数相乘，如果其中有一个因数能被某一个数整除，那么它们的积也能被这个数整除。
     *
     * 能被2整除的数，个位上的数能被2整除（偶数都能被2整除），那么这个数能被2整除
     *
     * 能被3整除的数，各个数位上的数字和能被3整除，那么这个数能被3整除
     *
     * 能被4整除的数，个位和十位所组成的两位数能被4整除，那么这个数能被4整除
     *
     * 能被5整除的数，个位上为0或5的数都能被5整除，那么这个数能被5整除
     *
     * 能被6整除的数，各数位上的数字和能被3整除的偶数，如果一个数既能被2整除又能被3整除，那么这个数能被6整除
     *
     * 能被7整除的数，若一个整数的个位数字截去，再从余下的数中，减去个位数的2倍，如果差是7的倍数，则原数能被7整除。如果差太大或心算不易看出是否7的倍数，就需要继续上述「截尾、倍大、相减、验差」的过程，直到能清楚判断为止。例如，判断133是否7的倍数的过程如下：13－3×2＝7，所以133是7的倍数；又例如判断6139是否7的倍数的过程如下：613－9×2＝595 ， 59－5×2＝49，所以6139是7的倍数，余类推。
     *
     * 能被8整除的数，一个整数的末3位若能被8整除，则该数一定能被8整除。
     *
     * 能被9整除的数，各个数位上的数字和能被9整除，那么这个数能被9整除
     *
     * 能被10整除的数，如果一个数既能被2整除又能被5整除，那么这个数能被10整除（即个位数为零）
     *
     * 能被11整除的数，奇数位（从左往右数）上的数字和与偶数位上的数字和之差（大数减小数）能被11整除，则该数就能被11整除。 11的倍数检验法也可用上述检查7的「割尾法」处理！过程唯一不同的是：倍数不是2而是1！
     * 能被12整除的数，若一个整数能被3和4整除，则这个数能被12整除
     *
     * 能被13整除的数，若一个整数的个位数字截去，再从余下的数中，加上个位数的4倍，如果差是13的倍数，则原数能被13整除。如果差太大或心算不易看出是否13的倍数，就需要继续上述「截尾、倍大、相加、验差」的过程，直到能清楚判断为止。
     *
     * 能被17整除的数，若一个整数的个位数字截去，再从余下的数中，减去个位数的5倍，如果差是17的倍数，则原数能被17整除。如果差太大或心算不易看出是否17的倍数，就需要继续上述「截尾、倍大、相减、验差」的过程，直到能清楚判断为止。
     *
     *    另一种方法：若一个整数的末三位与3倍的前面的隔出数的差能被17整除，则这个数能被17整除
     *
     * 能被19整除的数，若一个整数的个位数字截去，再从余下的数中，加上个位数的2倍，如果差是19的倍数，则原数能被19整除。如果差太大或心算不易看出是否19的倍数，就需要继续上述「截尾、倍大、相加、验差」的过程，直到能清楚判断为止。
     *
     * 另一种方法：若一个整数的末三位与7倍的前面的隔出数的差能被19整除，则这个数能被19整除
     *
     * 能被23整除的数，若一个整数的末四位与前面5倍的隔出数的差能被23(或29)整除，则这个数能被23整除
     *
     * 能被25整除的数，十位和个位所组成的两位数能被25整除。
     *
     * 能被125整除的数，百位、十位和个位所组成的三位数能被125整除。
     * @return
     */
    public static int Solution1() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        int N = Integer.valueOf(firstLine);
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        int fiveCount = 0;
        int zeroCount = 0;
        for (int i = 0; i < secondLineDetials.length; i ++) {
            int cur = Integer.valueOf(secondLineDetials[i]);
            if (cur == 5) {
                fiveCount ++;
            }
            if (cur == 0) {
                zeroCount ++;
            }
        }

        if (zeroCount <= 0 || 5 * fiveCount % 3 != 0) {
            return -1;
        }
        dfs(fiveCount, zeroCount - 1, "");
        return max;
    }

    private static void dfs(int fiveCount, int zeroCount, String temp) {
        if (zeroCount == 0 && fiveCount == 0) {
            int cur = Integer.valueOf(temp);
            if (cur % 9 == 0 && cur > max) {
                max = cur;
            }
            zeroCount --;
            return;
        }
        if (temp.length() == 0) {
            temp = temp + "5";
            fiveCount --;
            dfs(fiveCount, zeroCount, temp);
        } else {
            if (fiveCount > 0) {
                fiveCount --;
                temp = temp + "5";
                dfs(fiveCount, zeroCount, temp);
                temp = temp.substring(0, temp.length() - 1);
                fiveCount ++;
            }

            if (zeroCount > 0) {
                zeroCount --;
                temp = temp + "0";
                dfs(fiveCount, zeroCount, temp);
                temp = temp.substring(0, temp.length() - 1);
                zeroCount ++;
            }
        }
    }

    /**
     * 第一行输入T表示有T组测试样例
     * 接下来输入n, m表示有n头奶牛， m种特性（同时满足这m种特性才能称为优良奶牛）；
     * 接下来输入满足m种特性的奶牛编号区间：
     * 第一行输入k_1, 表示有k_1个区间满足
     * 输出： 第一行输入优良奶牛的头数； 第二行输出优良奶牛的编号
     * 例如：
     * 输入：
     * 1
     * 10 2
     * 2
     * 1 4
     * 5 8
     * 3
     * 1 2
     * 4 5
     * 8 8
     * 输出：
     * 4
     * 1 2 5 8
     */
    private static void Solution2() {
        Scanner scanner = new Scanner(System.in);
        String examplesCount = scanner.nextLine();
        for (int i = 0; i < Integer.valueOf(examplesCount); i ++) {
            String line2 = scanner.nextLine();
            String[] line2Detials = line2.split(" ");
            int n = Integer.valueOf(line2Detials[0]);
            int m = Integer.valueOf(line2Detials[1]);
            int maxMin = 1;
            int minMax = n;
            for (int j = 0; j < m; j ++) {
                int k = Integer.valueOf(scanner.next());
                for (int l = 0; l < k; l ++) {
                    String range = scanner.nextLine();
                    String[] rangeDetials = range.split(" ");
                    int start = Integer.valueOf(rangeDetials[0]);
                    int end = Integer.valueOf(rangeDetials[1]);
                    if (start > maxMin) {
                        maxMin = start;
                    }
                    if (end < minMax) {
                        minMax = end;
                    }
                }
            }
        }
    }

    private static boolean[] generateResults(int n) {
        boolean[] results = new boolean[n];
        for (int i = 0; i < n; i ++) {
            results[i] = false;
        }
        return results;
    }

    private static int maxM = 0;

    public static void solution3() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] lineDetials = line.split(" ");
        int n = Integer.valueOf(lineDetials[0]);
        int m = Integer.valueOf(lineDetials[1]);
        maxM = m;
        List<List<Integer>> results = new ArrayList<>();
        dfs(n, new ArrayList<>(), results);
        results.forEach(result -> {
            System.out.println(result.toString());
        });
    }

    private static void dfs(int left, List<Integer> temp, List<List<Integer>> results) {
        if (left == 0) {
            results.add(temp);
            return;
        }
        for (int i = 1; i <= maxM; i ++) {
            if (i >= left) {
                if (check(left, temp)) {
                    temp.add(left);
                    dfs(0, new ArrayList<>(temp), results);
                    temp.remove(temp.size() - 1);
                    break;
                }
            } else {
                if (check(i, temp)) {
                    temp.add(i);
                    dfs(left - i, new ArrayList<>(temp), results);
                    temp.remove(temp.size() - 1);
                }
            }
        }

    }

    private static boolean check(int cur, List<Integer> temp) {
        if (temp.size() == 0) {
            return true;
        }
        for (int i = temp.size() - 1; i >= 0 && i >= temp.size() - 2; i --) {
            int pre = temp.get(i);
            if (pre == cur) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        int result = Solution1();
//        System.out.println(result);
//        System.out.println(5555555550L % 3);
        solution3();
    }
}
