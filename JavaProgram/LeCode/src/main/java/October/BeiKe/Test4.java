package October.BeiKe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test4 {
    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < T; i ++) {
            List<Integer> As = new ArrayList<>();
            List<Integer> Bs = new ArrayList<>();
            String firstLine = scanner.nextLine();
            String[] firstLineDetials = firstLine.split(" ");
            int n = Integer.valueOf(firstLineDetials[0]);
            int m = Integer.valueOf(firstLineDetials[1]);
            for (int j = 0; j < m; j ++) {
                List<Integer> data = new ArrayList<>();
                String line = scanner.nextLine();
                String[] detials = line.split(" ");
                As.add(Integer.valueOf(detials[0]));
                Bs.add(Integer.valueOf(detials[1]));
            }
//            As.sort((num1, num2) -> {
//                return -(num1 - num2);
//            });
            int index = findMax(Bs);
            if (n == 1) {
                System.out.println(As.get(0));
            } else {
                System.out.println(As.get(index) + (n - 1) * Bs.get(index));
            }
        }
    }

    private static int findMax(List<Integer> Bs) {
        int index = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < Bs.size(); i ++) {
            int b = Bs.get(i);
            if (b > max) {
                max = b;
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        solution();
    }
}
