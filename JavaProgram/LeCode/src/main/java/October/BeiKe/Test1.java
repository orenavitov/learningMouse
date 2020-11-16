package October.BeiKe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test1 {
    private static void solution() {
        List<List<Integer>> testData = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int T = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < T; i ++) {
            String line = scanner.nextLine();
            String[] detials = line.split(" ");
            List<Integer> nums = new ArrayList<>();
            for (int j = 0; j < detials.length; j ++) {
                nums.add(Integer.valueOf(detials[j]));
            }
            testData.add(nums);

        }
        for (int i = 0; i < testData.size(); i ++) {
            List<Integer> nums = testData.get(i);
            int num1 = nums.get(0);
            int num2 = nums.get(1);
            int num3 = nums.get(2);
            if (num1 == num2) {
                if (num3 > num1) {
                    System.out.println("NO");
                }
                if (num3 <= num1) {
                    System.out.println("YES");
                    System.out.print(num3 + " ");
                    System.out.print(num3 + " ");
                    System.out.print(num1);
                }
            } else {
                if (num3 != Math.max(num1, num2)) {
                    System.out.println("NO");
                } else {
                    System.out.println("YES");
                    System.out.print(num2 + " ");
                    System.out.print(num1 + " ");
                    System.out.print(Math.min(num1, num2));
                }
            }
        }
    }

    public static void main(String[] args) {
        solution();
    }
}
