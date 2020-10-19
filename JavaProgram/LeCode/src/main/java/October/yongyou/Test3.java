package October.yongyou;

import java.util.Stack;

public class Test3 {
    public String decodeString (String str) {
        // write code here
        return null;
    }

    private static int getRepeatTime(String input) {
        Stack<Integer> repeatTimeStack = new Stack<>();
        for (int i = 0; i < input.length(); i ++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                repeatTimeStack.push(c - 48);
                continue;
            } else {
                return getRepeatTime(repeatTimeStack);
            }
        }
        return 0;
    }

    private static String getRepeatBody(String input) {
        int depth = 0;
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < input.length(); i ++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c == '[') {
                if(depth > 0) {
                    body.append(c);
                }
                depth ++;
                continue;
            }
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                body.append(c);
                continue;
            }
            if (c == ']') {
                depth --;
                if (depth == 0) {
                    return body.toString();
                } else {
                    body.append(c);
                }
            }
        }
        return null;
    }

    private static int getRepeatTime(Stack<Integer> repeatTime) {
        int result = 0;
        for (int i = 0; i < repeatTime.size(); i ++) {
            result = result + repeatTime.pop() * (int)Math.pow(10, i);
        }
        return result;
    }

    private static String dfs(String str, int repeatTime) {
        StringBuilder result = new StringBuilder();
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println((int)'0');
    }
}
