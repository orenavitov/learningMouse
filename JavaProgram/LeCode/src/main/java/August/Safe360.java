package August;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Safe360 {
    private static void Solution1() {
        int result = 0;
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        int n = Integer.valueOf(firstLine);
        for (int i = 0; i < n; i ++) {
            String line = scanner.nextLine();
            int length = line.length();
            boolean accept = true;
            if (length <= 10) {
                for (int j = 0; j < length; j ++) {
                    char c = line.charAt(j);
                    if (!isLetter((int)c)) {
                        accept = false;
                        break;
                    }
                }
                if (accept) {
                    result ++;
                }
            }
        }
        System.out.println(result);
    }

    private static boolean isLetter(int ascallNum) {
        if ((ascallNum >= 65 && ascallNum <= 90) || (ascallNum >= 97 && ascallNum <= 122)) {
            return true;
        }
        return false;
    }

    private static int length = 0;

    private static void Solution2() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int N = Integer.valueOf(firstLineDetials[0]);
        int M = Integer.valueOf(firstLineDetials[1]);
        length = N;
        String[] nums = new String[N];
        for(int i = 0; i < N; i ++) {
            nums[i] = new String("" + (i + 1));
        }

        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        for(int i = 0; i < M;) {
            String operate = secondLineDetials[i];
            if (operate.equals("1")) {
                change1(nums);
                i ++;
                continue;
            }
            if (operate.equals("2")) {
                if (i + 1 < M && secondLineDetials[i + 1].equals("2")) {
                    i = i + 2;
                    continue;
                }
                change2(nums);
                i ++;
            }
        }
        for(String num : nums) {
            System.out.print(num + " ");
        }
    }

    private static void change1(String[] nums) {
        String first = nums[0];
        for (int i = 1; i < length; i ++) {
            nums[i - 1] = nums[i];
        }
        nums[length - 1] = first;
    }

    private static void change2(String[] nums) {
        for(int i = 0; i < length / 2; i ++) {
            String temp = nums[i * 2];
            String next = nums[i * 2 + 1];
            nums[i * 2] = next;
            nums[i * 2 + 1] = temp;

        }
    }

    private static void Solution3() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int length = s.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length;) {
            char c = s.charAt(i);
            if (c == 'n' && stringBuilder.length() > 0) {
                System.out.println(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            } else {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append((char) ((int) c - 32));
                } else {
                    stringBuilder.append(c);
                }
                i ++;
            }


        }
        if (stringBuilder.length() > 0) {
            System.out.println(stringBuilder.toString());
        }
    }

    private static void Solution4() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int m = Integer.valueOf(firstLineDetials[0]);
        int n = Integer.valueOf(firstLineDetials[1]);
        int[][] herosPower = new int[m][n];
        for (int i = 0; i < m; i ++) {
            String line = scanner.nextLine();
            String[] powers = line.split(" ");
            for (int j = 0; j < n; j ++) {
                herosPower[i][j] = Integer.valueOf(powers[j]);
            }
        }
        Integer[][] itemPower = new Integer[n][m];
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < m; j ++) {
                itemPower[i][j] = herosPower[j][i];
            }

        }
        herosPower = null;
        for (int i = 0; i < n; i ++) {
            Integer[] powers = itemPower[i];
            Arrays.sort(powers, (power1, power2) -> {
                return -(power1 - power2);
            });
        }
        int[] results = new int[n + 1];
        for (int i = 0; i <= n; i ++) {
            results[i] = 0;
        }
        for (int i = 1; i <= n; i ++) {
            Integer[] powers = itemPower[i - 1];
            int count = 1;
            int start = 0;
            while (start <= n) {
                int next = start + i;
                if (next <= n && count <= m) {
                    if (results[next] < results[start] + powers[count - 1]) {
                        results[next] = results[start] + powers[count - 1];
                        count ++;
                    }
                }
                start ++;
            }

        }
        System.out.println(results[n]);
    }

    public static void main(String[] args) {

        Solution4();
    }
}
