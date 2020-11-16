package October.webank;

import java.util.Scanner;

public class Test2 {
    private static int solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int continueCount = 0;
        int missCount = 0;
        int result = 0;
        for (int i = 0; i < input.length(); i ++) {
            char c = input.charAt(i);
            if (c == 'P') {
                if (continueCount >= 3) {
                    result = result + 250;
                    continueCount ++;
                } else {
                    result = result + 200;
                    continueCount ++;
                }
            }
            if (c == 'G') {
                continueCount = 0;
                result = result + 100;
            }
            if (c == 'M') {
                continueCount = 0;
                missCount ++;
                if(missCount == 3) {
                    return 0;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int result = solution();
        System.out.println(result);
    }
}
