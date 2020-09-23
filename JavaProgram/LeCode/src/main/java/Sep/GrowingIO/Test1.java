package Sep.GrowingIO;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

public class Test1 {

    private static class Position {
        int x;
        int y;
        char val;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position)) {
                return false;
            }
            Position other = (Position) obj;
            if (other.x == this.x && other.y == this.y) {
                return true;
            }
            return false;
        }
    }

    public static int numIslands(char[][] grid) {
        int rowCount = grid.length;
        int colCount = grid[0].length;
        int result = 0;
        List<Position> visited = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j <  colCount; j++) {
                char cur = grid[i][j];
                if (cur == '1') {
                    Position position = new Position(i, j);
                    if (!visited.contains(position)) {
                        result++;
                        bfs(position, rowCount, colCount, visited, grid);
                    }
                }
            }
        }
        return result;
    }

    private static void bfs(Position position, int rowCount, int colCount, List<Position> visited, char[][] grid) {
        int x = position.x;
        int y = position.y;
        visited.add(position);

        // top
        Position top = new Position(x - 1, y);
        if (check(top, rowCount, colCount) && !visited.contains(top) && grid[x - 1][y] == '1') {
            bfs(top, rowCount, colCount, visited, grid);
        }
        // down
        Position down = new Position(x + 1, y);
        if (check(down, rowCount, colCount) && !visited.contains(down) && grid[x + 1][y] == '1') {
            bfs(down, rowCount, colCount, visited, grid);
        }
        // left
        Position left = new Position(x, y - 1);
        if (check(left, rowCount, colCount) && !visited.contains(left) && grid[x][y - 1] == '1') {
            bfs(left, rowCount, colCount, visited, grid);
        }
        // right
        Position right = new Position(x, y + 1);
        if (check(right, rowCount, colCount) && !visited.contains(right) && grid[x][y + 1] == '1') {
            bfs(right, rowCount, colCount, visited, grid);
        }

    }

    private static boolean check(Position position, int rowColunt, int colCount) {
        int x = position.x;
        int y = position.y;
        if (x >= 0 && x <= rowColunt - 1 && y >= 0 && y <= colCount - 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
//        char[][] grid = new char[][] {
//                {'1', '1', '1', '1', '0'},
//                {'1', '1', '0', '1', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '0', '0', '0'}
//        };
        System.out.println(numIslands(grid));
    }
}
