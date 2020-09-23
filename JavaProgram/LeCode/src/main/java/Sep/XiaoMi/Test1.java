package Sep.XiaoMi;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Test1 {
    private static void Solution(String input) {
        Stack<Character> stack = new Stack<>();
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char cur = input.charAt(i);
            if (cur == '(' || cur == '[' || cur == '{') {
                stack.push(cur);
            } else {
                if (stack.isEmpty()) {
                    System.out.println("false");
                    return;
                } else {
                    char top = stack.pop();
                    if ((cur == ')' && top != '(') || (cur == ']' && top != '[') || (cur == '}' && top != '{')) {
                        System.out.println("false");
                        return;
                    }
                }
            }
        }
        if (!stack.isEmpty()) {
            System.out.println("false");
            return;
        }
        System.out.println("true");
    }


    private static void Start() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            Solution(input);
        }
    }

    public static void main(String[] args) {

        Start();
    }
}
