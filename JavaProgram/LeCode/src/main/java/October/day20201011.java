package October;

import java.util.Arrays;

public class day20201011 {
    public static boolean canPartition(int[] nums) {
        int sum = 0;
        int length = nums.length;
        for (int i = 0; i < length; i ++) {
            sum += nums[i];
        }
        if (sum % 2 == 1) {
            return false;
        }
        Arrays.sort(nums);
        int max = nums[length - 1];
        if (max > sum / 2) {
            return false;
        }
        if (max == sum / 2) {
            return true;
        }
        boolean[][] results = new boolean[length][sum / 2 + 1];
        for (int i = 0; i < length; i ++) {
            for (int j = 0; j <= sum / 2; j ++) {
                if (j == 0) {
                    results[i][j] = true;
                } else {
                    results[i][j] = false;
                }
            }
        }
        results[0][nums[0]] = true;
        for (int i = 1; i < length; i ++) {
            int num = nums[i];
            for (int j = 0; j <= sum /2; j ++) {
                if (results[i - 1][j]) {
                    results[i][j] = true;
                } else {
                    if (j - num >= 0) {
                        if (results[i - 1][j - num]) {
                            results[i][j] = true;
                        }
                    }
                }
            }
        }
        return results[length - 1][sum / 2];
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5};
        System.out.println(canPartition(nums));
    }
}
