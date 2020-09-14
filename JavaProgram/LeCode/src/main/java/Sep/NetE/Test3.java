package Sep.NetE;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test3 {

    private static int max = 0;

    public static void Solution() {
        max = 0;
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int n = Integer.valueOf(firstLineDetials[0]);
        int k = Integer.valueOf(firstLineDetials[1]);
        String secondLine = scanner.nextLine();
        String[] secondLineDetials = secondLine.split(" ");
        int[] dsts = new int[n - 1];
        for (int i = 0; i < n - 1; i ++) {
            dsts[i] = Integer.valueOf(secondLineDetials[i]);
        }
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(0);
        dfs(0, dsts, visited, 0, k);

    }

    private static void dfs(int cur, int[] dsts, List<Integer> visited,
                            int cost, int k) {
        if (cost == k) {
            if (visited.size() > max) {
                max = visited.size();
            }
            return;
        }

        List<Integer> tempDsts = new ArrayList<>();
        // 可以到哪
        for (int i = 0; i < dsts.length; i ++) {
            if (dsts[i] == cur && !visited.contains(i + 1)) {
                tempDsts.add(i + 1);
            }
        }

        if (tempDsts.isEmpty()) {
            tempDsts.add(dsts[cur - 1]);
        }
        for (Integer dst : tempDsts) {
            visited.add(dst);
            dfs(dst, dsts, new ArrayList<>(visited), cost + 1, k);
            visited.remove(visited.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution();
        System.out.println(max);
    }
}
