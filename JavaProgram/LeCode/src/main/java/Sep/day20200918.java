package Sep;

import java.util.ArrayList;
import java.util.List;

public class day20200918 {
    public static List<List<Integer>> permuteUnique(int[] nums) {
        int length = nums.length;
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        for (int i = 0; i < nums.length; i ++) {
            left.add(nums[i]);
        }
        dfs(0, length, left, temp, results);
        return results;
    }

    private static void dfs(int curk, int length, List<Integer> left, List<Integer> temp, List<List<Integer>> results) {
        if (curk == length) {
            results.add(temp);
            return;
        }
        List<Integer> usedNum = new ArrayList<>();
        for(int i = 0; i < left.size(); i ++) {
            int curNum = left.get(i);
            if (!usedNum.contains(curNum)) {
                usedNum.add(curNum);
                temp.add(curNum);
                left.remove(i);
                dfs(curk + 1, length, left, new ArrayList<>(temp), results);
                left.add(i, curNum);
//                usedNum.remove(usedNum.size() - 1);
                temp.remove(temp.size() - 1);
            }

        }


    }

    public static void main(String[] args) {
        int[] nums = new int[] {1,1,2};
        List<List<Integer>> results = permuteUnique(nums);
        results.forEach(result -> {
            System.out.println(result.toString());
        });
    }
}
