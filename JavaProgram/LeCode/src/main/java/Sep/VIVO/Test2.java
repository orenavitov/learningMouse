package Sep.VIVO;

import java.util.Scanner;

public class Test2 {
    private static String Solution() {
        String result = "false";
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.length() == 1) {
            return  "";
        }
        int start = 0;
        int end = input.length() - 1;
        boolean used = false;
        int delIndex = 0;
        while (start < end) {
            char startChar = input.charAt(start);
            char endChar = input.charAt(end);
            if (startChar == endChar) {
                start++;
                end--;
                continue;
            } else {
                if (used) {
                    return "false";
                }
                if (input.charAt(start + 1) == endChar) {
                    delIndex = start;
                    start = start + 1;

                } else {
                    if (startChar == input.charAt(end - 1)) {
                        delIndex = end;
                        end = end - 1;

                    } else {
                        return "false";
                    }
                }

            }
            start++;
            end--;
        }
        result = input.substring(0, delIndex) + input.substring(delIndex + 1, input.length());
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Solution());
    }
}
