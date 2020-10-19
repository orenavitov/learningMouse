package October.GuangLianDa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test2 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String numsString = scanner.nextLine();
        String[] detials = numsString.split(" ");
        int[] nums = new int[detials.length];
        int sum = 0;
        for (int i =0 ; i < detials.length; i ++) {
            int num = Integer.valueOf(detials[i]);
            sum = sum + num;
            nums[i] = num;
        }
        List<Integer> biggerNums = new ArrayList<>();
        List<Integer> smallerNums = new ArrayList<>();
        int maxTarget = sum / detials.length;
        int tempTarget = maxTarget;
        while (tempTarget > 0) {
            for (int i = 0; i < nums.length; i ++) {
                int num = nums[i];
                if (num < tempTarget) {
                    smallerNums.add(num);
                }
                if (num > tempTarget) {
                    biggerNums.add(num);
                }
            }
            boolean tempResult = canBlance(smallerNums, biggerNums, tempTarget);
            if (tempResult) {
                System.out.println(tempTarget * 4);
                return;
            } else {
                tempTarget --;
            }
        }
        System.out.println(-1);
    }

    private static boolean canBlance(List<Integer> smallerNums, List<Integer> biggerNums,
                               int target) {
        int need = 0;
        int provide = 0;
        for (int i = 0; i < smallerNums.size(); i ++) {
            int smallerNum = smallerNums.get(i);
            need = need + (target - smallerNum);
        }
        for (int i = 0; i < biggerNums.size(); i ++) {
            int biggerNum = biggerNums.get(i);
            provide = provide + (biggerNum - target);
        }
        if (need * 2 <= provide) {
            return true;
        } else {
            return false;
        }
    }

    

    public static void main(String[] args) {
        Solution();
    }
}
