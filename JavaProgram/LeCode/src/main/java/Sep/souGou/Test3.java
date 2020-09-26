package Sep.souGou;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Test3 {


    public static class Interval {
        int start;
        int end;
        public  Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }


    public static Interval trim(int N, int M, Interval[] conn) {
        // write code here
        Set<Integer> startNums = new HashSet<>();
        Set<Integer> endNums = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        HashMap<Integer, Set<Integer>> preAndNext = new HashMap<>();
        for (int i = 0; i < M; i++) {
            Interval interval = conn[i];
            int pre = interval.start;
            int next = interval.end;
            if (pre == 0) {
                startNums.add(next);
                continue;
            }
            if (next == -1) {
                endNums.add(pre);
                continue;
            }
            if (!preAndNext.containsKey(pre)) {
                preAndNext.put(pre, new HashSet<>());
            }
            preAndNext.get(pre).add(next);
        }
        for (Integer start : startNums) {
            dfs(start, preAndNext, new HashSet<>(), visited, endNums);
        }
        int sum = 0;
        for (Integer num : visited) {
            sum = sum + num;
        }
        Interval interval = new Interval(visited.size(), sum % 100000007);
        return interval;
    }

    private static void dfs(int num, HashMap<Integer, Set<Integer>> preAndNext,
                            Set<Integer> tempVisited,
                            Set<Integer> visited,
                            Set<Integer> endNums) {
        if (endNums.contains(num)) {
            tempVisited.add(num);
            visited.addAll(tempVisited);
            return;
        }
        Set<Integer> nexts = preAndNext.get(num);

        if (nexts == null) {
            if (endNums.contains(num)) {
                tempVisited.add(num);
                visited.addAll(visited);
            }
            return;
        }
        for (Integer next : nexts) {
            tempVisited.add(next);
            Set<Integer> newTempVisited = new  HashSet<>();
            newTempVisited.addAll(tempVisited);
            dfs(next, preAndNext, newTempVisited, visited, endNums);
            tempVisited.remove(next);
        }
    }

    public static void main(String[] args) {
        int N = 3;
        int M = 4;
        Interval interval1 = new Interval(0, 1);
        Interval interval2 = new Interval(0, 2);
        Interval interval3 = new Interval(2, -1);
        Interval interval4 = new Interval(2, 1);
        Interval[] conn = new Interval[4];
        conn[0] = interval1;
        conn[1] = interval2;
        conn[2] = interval3;
        conn[3] = interval4;
        Interval result = trim(N, M, conn);
        System.out.println("end");
    }
}
