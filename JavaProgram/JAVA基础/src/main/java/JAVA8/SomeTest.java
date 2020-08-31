package JAVA8;

import java.util.concurrent.Executors;

/**
 * 给定一个整数数列列 a1，a2... an, 求出第2大的数（要求时间复杂度O(n)）
 */
public class SomeTest {

    public int getMMax(int[] nums) {
        int N = nums.length;
        int[] temp = new int[2];
        temp[0] = nums[0];
        temp[1] = nums[1];
        int tempMin = nums[0];
        int tempMinIndex = 0;
        if (nums[1] > tempMin) {
            tempMin = nums[1];
            tempMinIndex = 1;
        }
        for (int i = 3; i < N; i++) {
            if (nums[i] > tempMin) {
                tempMin = nums[i];
                temp[tempMinIndex] = tempMin;
//                tempMinIndex;
            }
        }
        return temp[1 - tempMinIndex];
    }

    public static void main(String[] args) {
        Executors.newFixedThreadPool(5)
        SomeTest someTest = new SomeTest();
        int[] nums = new int[]{20, 30, 13, 14, 54, 18, 19, 38, 80, 120};
        int result = someTest.getMMax(nums);
        System.out.println(result);
    }
}
