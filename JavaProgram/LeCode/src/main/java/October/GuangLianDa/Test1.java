package October.GuangLianDa;

import java.util.Scanner;

public class Test1 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String secondLine = scanner.nextLine();
        int length = firstLine.length();
        int result = 0;
        for (int i = 0; i < length; i ++) {
            char c1 = firstLine.charAt(i);
            char c2 = secondLine.charAt(i);
            if (c1 == c2) {
                result = result + 20;
            } else {
                if (result > 0) {
                    result = result - 10;
                }
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        Solution();
    }
}
