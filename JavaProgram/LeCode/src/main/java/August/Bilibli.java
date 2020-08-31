package August;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Bilibli {
    /**
     * 括号的合法匹配， 输入一个字符串， 返回是不是一个合法的括号
     * 例如：
     * 输入：“{[()]}”
     * 返回 true
     * 输入：“{[]()}”
     * 返回true
     * 输入：“{[]}(”
     * 返回false
     * @param s
     * @return
     */
    public static boolean IsValidExp (String s) {
        // write code here
        boolean result = true;
        int length = s.length();
        if (length == 0) {
            return result;
        }
        char firstChar = s.charAt(0);
        Stack<Character> characterStack = new Stack<>();
        characterStack.push(firstChar);
        for (int i = 1; i < length; i ++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                characterStack.push(c);
            } else  {
                if (characterStack.isEmpty()) {
                    return false;
                }
                char topChar = characterStack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }

        }
        if (characterStack.size() != 0) {
            return false;
        }
        return result;
    }

    /**
     * 输入1-9的4个数字，判断这4个数字能否通过加减乘除这四种运算获得24， 注意不能破坏数字的顺序， 即你只能在两个数字中间添加运算
     * 符号
     * 例如：
     * 输入【1， 2， 3， 4】
     * 因为1 * 2 * 3 * 4 = 24所以返回true;
     * 输入【2， 3， 7， 1】
     * 因为1 + 3 * 7 + 1 = 24所以返回true;
     * 输入【2， 3， 8， 1】
     * 返回false
     * @param arr
     * @return
     */
    public static boolean Game24Points (int[] arr) {
        // write code here
        boolean result = false;
        Character[] choosenOperators = new Character[] {'+', '-', '*', '/'};
        final int ALLTYPE = 4 * 4 * 4;
        ArrayList[] allOperators = new ArrayList[ALLTYPE];
        int count = 0;
        for (int i = 0; i < 4; i ++) {
            char firstOperator = choosenOperators[i];

            for(int j = 0; j < 4; j ++) {
                char secondOperator = choosenOperators[j];
                for (int k = 0; k < 4; k ++) {
                    char thirdOperator = choosenOperators[k];
                    ArrayList<Character> operatorsList = new ArrayList<>();
                    operatorsList.add(firstOperator);
                    operatorsList.add(secondOperator);
                    operatorsList.add(thirdOperator);
                    allOperators[count++] = operatorsList;
                }
            }
        }
        Stack<Float> operatorNumStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < ALLTYPE; i ++) {
            operatorNumStack.push((float) arr[0]);
            ArrayList operateorList = allOperators[i];
            System.out.println("i : " + i + " " + operateorList.toString());
            for (int j = 1; j < 4; j ++) {
                float currentNum = (float) arr[j];
                Character operator = (Character) operateorList.get(j - 1);
                if (operator == '+'){
                    operatorStack.push(operator);
                    operatorNumStack.push(currentNum);
                    continue;
                }
                if (operator == '-'){
                    operatorStack.push(operator);
                    operatorNumStack.push(currentNum);
                    continue;
                }
                if (operator == '*'){
                    float tempResult = operatorNumStack.pop() * currentNum;
                    operatorNumStack.push(tempResult);
                    continue;
                }
                if (operator == '/'){
                    if (currentNum == 0) {
                        operatorStack.clear();
                        operatorNumStack.clear();
                        break;
                    }
                    float tempResult = operatorNumStack.pop() / currentNum;
                    operatorNumStack.push(tempResult);
                    continue;
                }
            }
            while (!operatorStack.isEmpty()) {
                float firstNum = operatorNumStack.pop();
                float secondNum = operatorNumStack.pop();
                char operator = operatorStack.pop();
                if (operator == '+') {
                    operatorNumStack.push(secondNum + firstNum);
                }
                if (operator == '-') {
                    operatorNumStack.push(secondNum - firstNum);
                }
            }
            if (operatorNumStack.size() == 1) {
                float tempResult = operatorNumStack.pop();
                if (tempResult == 24.0) {
                    return true;
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {2, 3, 8, 1};
        System.out.println(Game24Points(arr));
    }
}
