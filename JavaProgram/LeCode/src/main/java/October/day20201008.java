package October;

public class day20201008 {
    public static void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;
        while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;
            end --;
            start ++;
        }

    }

    /**
     * 给定一个数组只有一个数重复了一次， 其他所有的数都重复了3次， 找出这个只重复了1次的数
     * @param nums
     */
    private static int findTheSingleNum(int[] nums) {
        int result = 0;
        for (int i = 0; i < 32; i ++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j ++) {
                sum = sum + ((nums[j] >> i) & 1);
            }
            if (sum % 3 == 1) {
                result = result + (1 << i);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums = {3, 3, 3, 5, 5, 5, 7};
        System.out.println(findTheSingleNum(nums));
    }
}
