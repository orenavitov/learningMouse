package August;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day20200801 {

    /**
     * 你有k个升序排列的整数数组。找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中。
     *
     * 我们定义如果b-a < d-c或者在b-a == d-c时a < c，则区间 [a,b] 比 [c,d] 小。
     *
     * 示例 1:
     *
     * 输入:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
     * 输出: [20,24]
     * 解释:
     * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
     * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
     * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
     * 注意:
     *
     * 给定的列表可能包含重复元素，所以在这里升序表示 >= 。
     * 1 <= k <= 3500
     * -105 <= 元素的值 <= 105
     *
     * 链接：https://leetcode-cn.com/problems/smallest-range-covering-elements-from-k-lists
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        int[] result = new int[2];
        int K = nums.size();
        int[] mins = new int[K];
        int[] sizes = new int[K];
        int[] indexes = new int[K];
        for (int i = 0; i < K; i ++) {
            mins[i] = nums.get(i).get(0);
            sizes[i] = nums.get(i).size();
            indexes[i] = 0;
        }
        int currentMinIndex = minIndex(mins);
        int currentMin = mins[currentMinIndex];
        int currentMaxIndex = max(mins);
        int currentMax = mins[currentMaxIndex];
        int currentWidth = currentMax - currentMin;
        while (!isEnd(indexes, sizes, K)) {
            int tempMinIndexNext = indexes[currentMinIndex] + 1;
            mins[currentMinIndex] = nums.get(currentMinIndex).get(tempMinIndexNext);
            indexes[currentMinIndex] = tempMinIndexNext;
            int tempMinIndex = minIndex(mins);
            int tempMin = mins[tempMinIndex];
            int tempMaxIndex = max(mins);
            int tempMax = mins[tempMaxIndex];
            if (tempMax - tempMin < currentWidth) {
                currentWidth = tempMax - tempMin;
                currentMinIndex = tempMinIndex;
                currentMin = tempMin;
                currentMaxIndex = tempMaxIndex;
                currentMax = tempMax;
            }
        }
        result[0] = currentMin;
        result[1] = currentMax;
        return result;
    }

    public boolean isEnd(int[] indexes, int[] sizes, int K) {
        for(int i = 0; i < K; i ++) {
            if (indexes[i] < sizes[i] - 1) {
                return false;
            }
        }
        return true;
    }

    public int minIndex(int[] nums) {
        int N = nums.length;
        int tempMin = nums[0];
        int tempMinIndex = 0;

        for (int i = 0; i < N; i ++) {
            if (nums[i] < tempMin) {
                tempMin = nums[i];
                tempMinIndex = i;
            }
        }
        return tempMinIndex;
    }

    public int max(int[] nums) {
        int N = nums.length;
        int tempMax = nums[0];
        int tempMaxIndex = 0;
        for (int i = 0; i < N; i ++) {
            if (nums[i] > tempMax) {
                tempMax = nums[i];
                tempMaxIndex = i;
            }
        }
        return tempMaxIndex;
    }

    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        List<Integer> subNums1 = Arrays.asList(4,10,15,24,26);
        List<Integer> subNums2 = Arrays.asList(0,9,12,20);
        List<Integer> subNums3 = Arrays.asList(5,18,22,30);

        nums.add(subNums1);
        nums.add(subNums2);
        nums.add(subNums3);
        day20200801 today = new day20200801();
        int[] result = today.smallestRange(nums);
        System.out.println("[" + result[0] + ", " + result[1] + "]");
    }

}
