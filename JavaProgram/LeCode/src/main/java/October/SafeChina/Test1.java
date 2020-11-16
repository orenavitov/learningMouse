package October.SafeChina;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Test1 {
    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] detials = input.split(" ");
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < detials.length; i ++) {
            int num = Integer.valueOf(detials[i]);
            if (!nums.contains(num)) {
                nums.add(num);
            }
        }
        nums.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -(o1 - o2);
            }
        });
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }

    public static void main(String[] args) {
        solution();
    }
}
