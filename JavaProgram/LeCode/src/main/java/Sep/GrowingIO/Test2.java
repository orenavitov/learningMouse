package Sep.GrowingIO;

import java.util.Scanner;

public class Test2 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        int q = Integer.valueOf(scanner.nextLine());
        for (int i = 0; i < q; i++) {
            String a = scanner.nextLine();
            String b = scanner.nextLine();
            if (possilble1(a, b) || possilble1(b, a)){
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }

        }
    }

    private static boolean possilble1(String a, String b) {
        int startA = 0;
        int endA = a.length();
        int startB = 0;
        int endB = b.length();
        while (startA < endA && startB < endB) {
            char charA = a.charAt(startA);
            char charB = b.charAt(startB);
            if (charA == charB) {
                startA++;
                startB++;
            } else {
                // 小写字母的情况
                if (charA >= 97 && charA <= 122) {
                    if (charA - 32 == charB) {
                        startA++;
                        startB++;
                    } else {
                        startA++;
                    }
                } else {
                    // 大写字母的情况
                    break;
                }
            }
        }

        if (startB == endB) {
            boolean hasLeft = false;
            while (startA < endA) {
                char charA = a.charAt(startA);
                if (!(charA >= 97 && charA <= 122)) {
                    hasLeft = true;
                    break;
                }
                startA++;
            }
            if (hasLeft == true) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Solution();
//        System.out.println((int) 'a');
//        System.out.println((int) 'z');
//        System.out.println((int) 'A');
    }
}
