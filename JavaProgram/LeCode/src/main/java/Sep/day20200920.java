package Sep;

import java.util.ArrayList;
import java.util.List;

public class day20200920 {
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        int maxCount = nums.length;
        for (int i = 0; i <= maxCount; i ++) {
            dfs(0, maxCount, 0, i, new ArrayList<>(), nums, results);
        }
        return results;
    }

    private static void dfs(int start, int end,int count,
                            int maxCount, List<Integer>temp, int[] nums,
                            List<List<Integer>> results) {
        if (count == maxCount) {
            results.add(temp);
            return;
        }
        for (int i = start; i < end; i ++) {
            int num = nums[i];
            temp.add(num);
            dfs(i + 1, end, count + 1, maxCount, new ArrayList<>(temp),
                    nums, results);
            temp.remove(temp.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1};
        List<List<Integer>> results = subsets(nums);
        results.forEach(result -> {
            System.out.println(result);
        });
    }

}
