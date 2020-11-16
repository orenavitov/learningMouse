package October.SafeChina;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Test2 {

    private static class Position {
        int x;
        int y;

        public Position() {

        }

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position)) {
                return false;
            } else {
                Position other = (Position) obj;
                if (other.x == this.x && other.y == this.y) {
                    return true;
                }
            }
            return false;
        }
    }

    private static void solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int N = Integer.valueOf(input);
        LinkedList<Integer> xLeft = new LinkedList<>();
        LinkedList<Integer> yLeft = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            xLeft.add(i);
            yLeft.add(i);
        }
        LinkedList<Position> positions = new LinkedList<>();
        LinkedList<LinkedList<Position>> results = new LinkedList<>();
        dfs(0, N, xLeft, yLeft, positions, results);
        System.out.println("" + results.size());
    }

    static int result = 0;
    private static void dfs(int n, int N, LinkedList<Integer> xLeft,
                            LinkedList<Integer> yLeft,
                            LinkedList<Position> positions,
                            LinkedList<LinkedList<Position>> results) {
        if (n == N - 1) {
            int x = xLeft.getLast();
            int y = yLeft.getLast();
            if (check(x, y, positions)) {
                Position position = new Position(x, y);
                positions.add(position);
                results.add(positions);
            }
            return;
        }

        for (int j = 0; j < yLeft.size(); j++) {
            int y = yLeft.remove(j);
            if (check(n, y, positions)) {
                Position position = new Position(n, y);
                if (!positions.contains(position)) {
                    positions.addLast(position);
                    dfs(n + 1, N, xLeft, yLeft, new LinkedList<>(positions), results);
                    positions.removeLast();
                }
                position = null;
            }
            yLeft.add(j, y);
        }

    }

    private static boolean check(int x, int y, List<Position> positions) {
        for (Position position : positions) {
            if (Math.abs(position.x - x) == Math.abs(position.y - y)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        solution();
    }
}
