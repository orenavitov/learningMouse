package Sep.DiDi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Test1 {
    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.valueOf(scanner.nextLine());
        ArrayList<Integer> visited = new ArrayList<>();
        HashMap<Integer, List<Integer>> bridges = new HashMap<>();
        for (int i = 0; i < T; i ++) {
            String firstLine = scanner.nextLine();
            String[] firstLineDetials = firstLine.split(" ");
            int n = Integer.valueOf(firstLineDetials[0]);
            int m = Integer.valueOf(firstLineDetials[1]);
            int k = Integer.valueOf(firstLineDetials[2]);
            if (n == 1) {
                System.out.println("Yes");
                continue;
            }

            for (int j = 0; j < m; j ++) {
                String line = scanner.nextLine();
                String[] detials = line.split(" ");
                int src = Integer.valueOf(detials[0]);
                int dst = Integer.valueOf(detials[1]);
                int cost = Integer.valueOf(detials[2]);
                if (cost <= k) {
                    if (!bridges.containsKey(src)) {
                        bridges.put(src, new ArrayList<>());
                    }
                    bridges.get(src).add(dst);
                }
            }
            int start = 1;
            dfs(bridges, start, visited);
            if (visited.size() == n) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
            bridges.clear();
            visited.clear();
        }
    }

    private static void dfs(HashMap<Integer, List<Integer>> bridges, int curLand, List<Integer> visited) {
        visited.add(curLand);
        List<Integer> neighbors = bridges.get(curLand);
        if (neighbors == null) {
            return;
        }
        for (Integer neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
//                visited.add(neighbor);
                dfs(bridges, neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {
        Solution();
    }
}
