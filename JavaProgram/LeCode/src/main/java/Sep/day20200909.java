package Sep;

import java.util.ArrayList;
import java.util.List;

public class day20200909 {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        int length = candidates.length;
        List<List<Integer>>[] results = new ArrayList[target + 1];
        for (int i = 0; i <= target; i ++) {
            results[i] = new ArrayList<>();
        }

        results[0].add(new ArrayList<>());
        for (int i = 0; i < length; i ++) {
            int candidate = candidates[i];

            for(int j = candidate; j <= target; j ++) {
                List<List<Integer>> preResult = results[j - candidate];
                if (preResult != null) {
                    List<List<Integer>> result = new ArrayList<>();
                    for (List<Integer> temp : preResult) {
                        List<Integer> newTemp = new ArrayList<>(temp);
                        newTemp.add(candidate);
                        result.add(newTemp);
                    }
                    results[j].addAll(result);
                }

//                j = j + 1;
            }
        }
        return results[target];
    }

    public static void main(String[] args) {
        int[] candidates = new int[]{2,3,6,7};
        int target = 8;
        List<List<Integer>> result = combinationSum(candidates, target);
        result.forEach(row -> {
            System.out.println(row.toString());
        });
    }
}
