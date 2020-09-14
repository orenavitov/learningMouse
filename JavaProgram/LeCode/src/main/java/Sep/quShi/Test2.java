package Sep.quShi;

import java.util.Scanner;
import java.util.Stack;

public class Test2 {
    public static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String nums = scanner.nextLine();
        int x = Integer.valueOf(scanner.nextLine());
        Stack<Character> stack = new Stack<>();
        int usedTime = 0;
        for(int i = 0; i < nums.length(); i ++) {
            char cur = nums.charAt(i);
            if (stack.isEmpty()) {
                stack.push(cur);
                continue;
            }
            char top = stack.peek();
            while (top > cur && usedTime < x) {
                stack.pop();
                usedTime ++;
                if (!stack.isEmpty()) {
                    top = stack.peek();
                } else {
                    break;
                }

            }
            stack.push(cur);
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        System.out.println(result.reverse().toString());
    }

    public static void main(String[] args) {
        Solution();
    }
}
