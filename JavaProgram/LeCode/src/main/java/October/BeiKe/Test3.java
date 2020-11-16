package October.BeiKe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Test3 {
    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.valueOf(scanner.nextLine());
        List<String[]> testData = new ArrayList<>();
        for (int i = 0; i < T; i ++) {
            String line = scanner.nextLine();
            testData.add(line.split(" "));
        }
        for (String[] bonds : testData) {
            check(bonds);
        }
    }

    private static void check(String[] bonds) {
        int leftBond = Integer.valueOf(bonds[0]);
        int rightBond = Integer.valueOf(bonds[1]);
        List<Integer> tempResult = new ArrayList<>();
        IntStream.rangeClosed(leftBond, rightBond).forEach(num -> {
            String numStr = String.valueOf(num);
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < numStr.length(); i ++) {
                char c = numStr.charAt(i);
                min = Math.min(min, c - 48);
                max = Math.max(max, c - 48);
            }
            if (min * 2 >= max) {
                tempResult.add(num);
            }
        });
        System.out.println(tempResult.size());
    }

    public static void main(String[] args) {
        solution();
    }
}
