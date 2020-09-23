package Sep.WeiPinHui;

import java.util.Scanner;

public class Test1 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.charAt(0) == '-' || input.charAt(0) == '+') {
            StringBuilder stringBuilder = new StringBuilder(input.substring(1));
            String result = stringBuilder.reverse().toString();
            if (input.charAt(0) == '-') {
                System.out.println(-1 * Integer.valueOf(result));
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder(input);
            String result = stringBuilder.reverse().toString();
            System.out.println(Integer.valueOf(result));
        }

    }

    public static void main(String[] args) {
        Solution();
    }
}
