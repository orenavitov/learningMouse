package August;

import java.util.*;

public class TX {
    private static void Solution1() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int n = Integer.valueOf(firstLineDetials[0]);
        int k = Integer.valueOf(firstLineDetials[1]);

        LinkedList<String> nums = new LinkedList<>();
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        for(int i = 0; i < n; i ++) {
            nums.addLast(secondLineDetials[i]);
        }
        nums.remove(k - 1);
        for(int i = 0; i < n - 1;i ++) {
            System.out.print(nums.get(i) + " ");
        }
    }

    /**
     * 输入一个字符串， 求这个字符串的不同子串中字典序最小的第k个子串
     * 如 输入“aabb”
     * 子串有"a", "aa", "aab", "aabb", "ab", "abb", "b", "bb"
     * 如果k = 3
     * 则输出为aab
     */
    private static void Solution2() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> strings = new ArrayList<>();
        String s = scanner.nextLine();
        int length = s.length();
        for (int i = 0; i < length; i ++) {
            for (int j = i; j < length; j ++) {
                String subString = s.substring(i, j + 1);
                if (!strings.contains(subString)) {
                    strings.add(subString);
                }

            }
        }
        strings.sort((s1, s2) -> {
            int l1 = s1.length();
            int l2 = s2.length();
            for (int i = 0; i < Math.min(l1, l2); i ++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                int i1 = (int)c1;
                int i2 = (int)c2;
                if (i1 != i2) {
                    return  i1 - i2;
                }
            }
            if (l2 > l1) {
                return -1;
            } else {
                return 1;
            }
        });
        int k = Integer.valueOf(scanner.nextLine());
        System.out.println(strings.get(k - 1));
    }

    private static void Solution3() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        int T = Integer.valueOf(firstLine);
        for (int i = 0; i < T; i ++) {
            int num = Integer.valueOf(scanner.nextLine());
            int max = getMaxValue(num);
            System.out.println(max);
        }
    }

    private static int getMaxValue(int num) {
        int max = 0;
        int left = 0;
        int right = 0;
        if (num % 2 == 0) {
            left = num / 2;
            right = num / 2;
        } else {
            left = num / 2;
            right = num / 2 + 1;
        }
        while (left >= 0) {
            int curValue = getValue(left) + getValue(right);
            if (curValue > max) {

                max = curValue;
                System.out.println("left : " + left + " " + "right: " + right + " " + "max : " + max);
            }
            left --;
            right ++;
        }
        return max;
    }

    private static int getValue(int num) {
        int value = 0;
        while (num / 10 > 0) {
            value = num % 10 + value;
            num = num / 10;
        }
        return value + num;
    }

    /**
     * 刷木板： 每块木板的宽度都是1， 高度各不相同， 现有一个刷子， 可以横向刷也可以纵向刷
     */
    private static void Solution4() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        int n = Integer.valueOf(firstLine);
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        int[] heights = new int[n];
        int maxHeght = 0;
        for(int i = 0; i < n; i ++) {
            int height = Integer.valueOf(secondLineDetials[i]);
            heights[i] = height;
            if (height > maxHeght) {
                maxHeght = height;
            }
        }
        int result = 0;
        for(int i = 0; i < maxHeght; i ++) {
            boolean prePrinted = true;
            boolean preDevide = false;
            for (int j = 0; j < n; j ++) {
                if (j == n - 1 && prePrinted && heights[j] > 0) {
                    heights[j] = heights[j] - 1;
                    result ++;
                    break;
                }
                if (heights[j] > 0) {

                    heights[j] = heights[j] - 1;
                    if (preDevide) {
                        result ++;
                        preDevide = false;
                    }
                    prePrinted = true;

                } else {
                    preDevide = true;
                }
            }
        }
        System.out.println(result);

    }

    /**
     * 输入一个字符串， 求这个字符串最少可以分割成多少个回文串
     * 如： 输入“aabb”, 则输出1；
     * 输入“aabc”, 则输出3；
     * @param
     */
    private static int Solution5(String s) {
        int n = s.length();
        int[] results = new int[n + 1];
        results[0] = 0;
        if (n == 1) {
            return 1;
        }
        for (int i = 1; i <= n; i ++) {
            results[i] = Integer.MAX_VALUE;
        }
        for(int i = 1; i <= n; i ++) {
            if (check(s.substring(0, i))) {
                results[i] = 1;

            } else {
                for(int j = i - 1; j >= 1; j --) {
                    if (check(s.substring(j, i))) {
                        if (results[j] + 1 < results[i]) {
                            results[i] = results[j] + 1;
                        }
                    }
                }
            }
        }
        return results[n];
    }

    private static boolean check(String s) {
        int length = s.length();
        int start = 0;
        int end = length - 1;
        boolean result = true;
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                result = false;
                break;
            }
            start ++;
            end --;
        }
        return result;
    }

    public static void main(String[] args) {
//        String s = "aabb";
//        System.out.println(Solution5(s));
        Solution3();
    }
}
