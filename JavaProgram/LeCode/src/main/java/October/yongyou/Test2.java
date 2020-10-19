package October.yongyou;

import java.util.Stack;

public class Test2 {
    public static boolean checkStr (String str) {
        // write code here
        Stack<Character> stack = new Stack<>();
        int temp = 0;
        for (int i = 0; i < str.length(); i ++) {
            char c = str.charAt(i);
            if (c == '{') {
                stack.push(c);
                temp = 0;
                continue;
            }
            if (c == '}') {
                if (stack.isEmpty() && temp == 0) {
                    return false;
                } else {
                    if (stack.isEmpty()) {
                        temp --;
                        continue;
                    } else {
                        stack.pop();
                        continue;
                    }
                }
            }
            if (c == '*') {
                temp ++;
            }
        }
        if (!stack.isEmpty()) {
            if (temp != stack.size()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String input = "{*}}";
        System.out.println(checkStr(input));
    }
}
