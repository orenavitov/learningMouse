package Sep;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class day20200913 {

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position)) {
                return false;
            }
            Position another = (Position) obj;
            if (another.x == this.x && another.y == this.y) {
                return true;
            }
            return false;
        }
    }

    public static boolean exist(char[][] board, String word) {
        char startChar = word.charAt(0);
        int rowCount = board.length;
        int colCount = board[0].length;
        List<Position> startPositions = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (startChar == board[i][j]) {
                    startPositions.add(new Position(i, j));
                }
            }
        }
        if (startPositions.isEmpty()) {
            return false;
        }
        List<Position> visited = new ArrayList<>();
        for (Position position : startPositions) {
            visited.add(position);
            if (dfs(position, 1, word.length(), rowCount, colCount, board, visited, word)) {
                return true;
            }
            visited.clear();
        }
        return false;
    }

    private static boolean dfs(Position curPosition, int next, int length, int rowCount, int colCount,
                               char[][] board, List<Position> visited, String word) {
        if (next == length) {
            return true;
        }

        int x = curPosition.x;
        int y = curPosition.y;
        char nextChar = word.charAt(next);
        boolean topResult = false;
        boolean downResult = false;
        boolean leftResult = false;
        boolean rightResult = false;
        if (checkPosition(x - 1, y, rowCount, colCount) && board[x - 1][y] == nextChar) {
            Position top = new Position(x - 1, y);
            if (!visited.contains(top)) {
                visited.add(top);
                topResult = dfs(top, next + 1, word.length(), rowCount, colCount, board, visited, word);
                if (topResult) {
                    return true;
                }
                visited.remove(visited.size() - 1);
            }
        }

        if (checkPosition(x + 1, y, rowCount, colCount) && board[x + 1][y] == nextChar) {
            Position down = new Position(x + 1, y);
            if (!visited.contains(down)) {
                visited.add(down);
                downResult = dfs(down, next + 1, word.length(), rowCount, colCount, board, visited, word);
                if (downResult) {
                    return true;
                }
                visited.remove(visited.size() - 1);
            }
        }

        if (checkPosition(x, y - 1, rowCount, colCount) && board[x][y - 1] == nextChar) {
            Position left = new Position(x, y - 1);
            if (!visited.contains(left)) {
                visited.add(left);
                leftResult = dfs(left, next + 1, word.length(), rowCount, colCount, board, visited, word);
                if (leftResult) {
                    return true;
                }
                visited.remove(visited.size() - 1);
            }
        }

        if (checkPosition(x, y + 1, rowCount, colCount) && board[x][y + 1] == nextChar) {
            Position right = new Position(x, y + 1);
            if (!visited.contains(right)) {
                visited.add(right);
                rightResult = dfs(right, next + 1, word.length(), rowCount, colCount, board, visited, word);
                if (rightResult) {
                    return true;
                }
                visited.remove(visited.size() - 1);
            }
        }
        return false;

    }

    private static boolean checkPosition(int x, int y, int rowCount, int colCount) {
        if (x >= 0 && x < rowCount && y >= 0 && y < colCount) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "SEE";
        System.out.println(exist(board, word));

    }

}
