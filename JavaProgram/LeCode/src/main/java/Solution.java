import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solution {

    private static int Solution1(int num) {
        int step = 0;
        while (num != 0) {

            if (num % 2 == 1) {
                num = num - 1;
                step ++;
            }
            else {
                num = num / 2;
                step ++;
            }
        }

        return step;
    }


    private static int Solution2(List<Integer> tree) {
        Stack<Integer> stack = new Stack<>();
        int index = 0;
        stack.push(index);
        int length = tree.size();
        int sum = 0;
        while (!stack.isEmpty()) {
            int top = stack.pop();
            int leftChild = top * 2 + 1;
            int rightChild = top * 2 + 2;
            if (rightChild <= (length - 1)) {
                if (tree.get(leftChild) != null) {
                    stack.push(leftChild);
                }
                if(tree.get(rightChild) != null) {
                    stack.push(rightChild);
                }
                if (tree.get(leftChild) == null && tree.get(rightChild) == null) {
                    sum = sum + tree.get(top);
                }
            }

        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> tree = Arrays.asList(1,2,3,4,5,null,6,7,null,null,null,null,8);
        int sum = Solution2(tree);
        System.out.println("sum is : " + sum);
    }

}
