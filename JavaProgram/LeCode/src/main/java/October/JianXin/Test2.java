package October.JianXin;

import java.util.HashSet;
import java.util.LinkedList;

public class Test2 {
    public static int findFriendNum (int[][] M) {
        // write code here
        HashSet<Integer> visited = new HashSet<>();
        int N = M.length;
        int result = 0;
        for (int i = 0; i < N; i ++) {
            if (!visited.contains(i)) {
                visited.add(i);
                bfs(i, M, visited);
                result ++;
            }

        }
        return result;
    }

    private static void bfs(int cur, int[][] M, HashSet<Integer> visited) {
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(cur);
        while (!que.isEmpty()) {
            int first = que.poll();
            int[] neighbors = M[first];
            for (int i = 0; i < neighbors.length; i ++) {
                if (neighbors[i] == 1) {
                    if (!visited.contains(i)) {
                        visited.add(i);
                        que.addLast(i);
                    }
                }

            }
        }
    }

    public static void main(String[] args) {
        int[][] M = {{1,1,0},{1,1,0},{0,0,1}};
//        int[][] M = {{1}};
        System.out.println(findFriendNum(M));
    }
}
