package October.webank;

import java.util.Scanner;

public class Test3 {

    private static int result = 0;

    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());
        String secondLine = scanner.nextLine();
        String[] numbersStr = secondLine.split(" ");
        int[] numbers = new int[n];
        for (int i = 0; i < n; i ++) {
            numbers[i] = Integer.valueOf(numbersStr[i]);
        }
        for (int i = 0; i < numbers.length; i ++) {
            dfs(i + 1, 1, numbers[i], numbers);
        }
    }

    private static void dfs(int startIndex, int deep, int pre, int[] numbers) {
        if (deep == 3) {
            result ++;
            return;
        }
        for (int i = startIndex; i < numbers.length; i ++) {
            int curNum = numbers[i];
            if (curNum >= pre) {
                dfs(i + 1, deep + 1, curNum, numbers);
            }
        }
    }

    public static void main(String[] args) {
        solution();
        System.out.println(result);
    }
}
