package Sep;

import java.util.*;

public class day20200910 {
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> left = new LinkedList<>();
        Arrays.sort(candidates);
        for (int i = 0; i < candidates.length; i ++) {
            left.add(candidates[i]);
        }
        dfs(0, 0, left, new ArrayList<>(), target, results);
        return results;
    }

    private static void dfs(int cur, int start, List<Integer> left, List<Integer> temp, int target,
                            List<List<Integer>> result) {
        if (cur == target) {
            result.add(temp);
            return;
        }
        if (cur > target) {
            return;
        }
        int pre = -1;
        for (int i = start; i < left.size(); i ++) {
            int num = left.get(i);
            if (num == pre) {
                continue;
            }
            temp.add(num);
            left.remove(i);
            dfs(cur + num, i, left, new ArrayList<>(temp), target, result);
            left.add(i, num);
            temp.remove(temp.size() - 1);
            pre = num;
        }
    }

    public static void main(String[] args) {
        int[] candidates = new int[] {2,5,2,1,2};
        int target = 5;
        List<List<Integer>> results = combinationSum2(candidates, target);
        results.forEach(result -> {
            System.out.println(result.toString());
        });
    }
}
