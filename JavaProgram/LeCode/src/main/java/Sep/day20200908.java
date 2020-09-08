package Sep;

import java.util.ArrayList;
import java.util.List;

public class day20200908 {
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> results = new ArrayList<>();
        dfs(1, n, k, new ArrayList<>(), results);
        return results;
    }

    private static void dfs(int start, int n, int k, List<Integer> temp, List<List<Integer>> results) {
        if (temp.size() == k) {
            results.add(temp);
            return;
        }
        for (int i = start; i <= n; i++) {
            temp.add(i);
            dfs(i + 1, n, k, new ArrayList<>(temp), results);
            temp.remove(temp.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> results = combine(4,2);
        results.forEach(result -> {
            System.out.println(result);
        });
    }
}
