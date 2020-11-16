package October.BeiKe;

import java.util.HashSet;
import java.util.Scanner;

public class Test2 {
    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < T; i ++) {
            String firstLine = scanner.nextLine();
            String[] firstLineDetials = firstLine.split(" ");
            int m = Integer.valueOf(firstLineDetials[0]);
            int n = Integer.valueOf(firstLineDetials[1]);
            HashSet<Integer> nums = new HashSet<>();
            String secondLine = scanner.nextLine();
            String[] secondLineDetials = secondLine.split(" ");
            boolean find = false;
            for (int j = 0; j < secondLineDetials.length; j ++) {
                int cur = Integer.valueOf(secondLineDetials[j]);
                if (n % cur == 0 && nums.contains(n / cur)) {
                    System.out.println("yes");
                    find = true;
                    break;
                }
                nums.add(Integer.valueOf(secondLineDetials[j]));
            }
//            boolean find = false;
//            for (Integer num : nums) {
//                if (n % num == 0 && nums.contains(n / num)) {
//                    System.out.println("yes");
//                    find = true;
//                    break;
//                }
//            }
            if (!find) {
                System.out.println("no");
            }
        }
    }

    public static void main(String[] args) {
        solution();
    }
}
