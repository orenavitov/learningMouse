package FutureTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Test {
    private static void reverseList(List<Integer> nums) {
        Stack<Integer> result = new Stack();
        int N = nums.size();
        for(Integer num : nums) {
            result.push(num);
        }
        for (int i = 0; i < N; i ++) {
            System.out.println(result.pop());
        }

    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
        List<Integer> nums = new ArrayList();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
        reverseList(nums);
    }
}
