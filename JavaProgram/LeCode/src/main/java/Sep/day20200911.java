package Sep;

import java.util.ArrayList;
import java.util.List;

public class day20200911 {
    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> results = new ArrayList<>();
        dfs(0, 0, k, n, 1, new ArrayList<>(), results);
        return results;
    }

    private static void dfs(int tempSum, int curK, int k, int n, int start,
                            List<Integer> tempResult, List<List<Integer>> results) {
        if(curK == k) {
            if (tempSum == n) {
                results.add(tempResult);
            }
            return;
        }
        for (int i = start; i <= 9; i ++) {
            tempResult.add(i);
            dfs(tempSum + i, curK + 1, k, n, i + 1,
                    new ArrayList<>(tempResult), results);
            tempResult.remove(tempResult.size() - 1);
        }
    }

    public static void main(String[] args) {
        int k = 3;
        int n = 7;
        List<List<Integer>> results = combinationSum3(k, n);
        results.forEach(result -> {
            System.out.println(result.toString());
        });
    }
}
