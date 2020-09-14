package Sep.DiDi;

import java.util.Scanner;
import java.util.Stack;

public class Test2 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());
        String input = scanner.nextLine();
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        int temp = 1;
        for (int i = 0; i < input.length(); i ++) {
            char cur = input.charAt(i);
            stack.push(cur);
            if (temp == n) {
                while (!stack.isEmpty()) {
                    result.append(stack.pop());
                }
                temp = 1;
                continue;
            }
            temp = temp + 1;
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        System.out.println(result.toString());
    }

    public static void main(String[] args) {
        Solution();
    }
}
