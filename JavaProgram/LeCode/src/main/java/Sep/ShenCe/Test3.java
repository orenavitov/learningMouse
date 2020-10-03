package Sep.ShenCe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test3 {
    public static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int[] moneyCounts = new int[firstLine.length()];
        int[] moneys = new int[] {1, 2, 5, 10, 20, 50, 100};
        for (int i = 0; i < firstLineDetials.length; i ++) {
            moneyCounts[i] = Integer.valueOf(firstLineDetials[i]);
        }
        String secondLLine = scanner.nextLine();
        int target = Integer.valueOf(secondLLine);
        List<List<Integer>> results = new ArrayList<>();
        dfs(0, moneys.length, 0, target, moneyCounts, moneys, new ArrayList<>(), results);
        results.forEach(result -> {
            int length = result.size();
            for (int i = 0; i < length; i ++) {
                System.out.print(result.get(i) + " ");
            }
            if (length < moneys.length) {
                for (int j = 0; j < moneys.length - length; j ++) {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        });
    }

    private static void dfs(int curIndex, int length, int curSum,
                            int target, int[] moneyCounts,
                            int[] moneys,
                            List<Integer> temp,
                            List<List<Integer>> results) {
        if (curIndex == length) {
            if (target == curSum) {
                results.add(temp);
            }
            return;
        }
        int moneyCount = moneyCounts[curIndex];
        int money = moneys[curIndex];
        for (int i = 0; i <= moneyCount; i ++) {
            if (money * i + curSum == target) {
                temp.add(i);
                results.add(temp);
                return;
            } else {
                temp.add(i);
                dfs(curIndex + 1, length, curSum + i * money,  target,
                        moneyCounts, moneys, new ArrayList<>(temp), results);
                temp.remove(temp.size() - 1);
            }
        }

    }

    public static void main(String[] args) {
        Solution();
    }
}
