package August;


import java.util.LinkedList;

public class day20200820 {

    private static LinkedList<Position> unvisited = new LinkedList<>();

    private static class Position {
        int x;

        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
    /**
     * 让我们一起来玩扫雷游戏！
     * 给定一个代表游戏板的二维字符矩阵。'M'代表一个未挖出的地雷，'E'代表一个未挖出的空方块，'B'代表没有相邻（上，下，左，右，和所有4个对角线）地雷的已挖出的空白方块，数字（'1' 到 '8'）表示有多少地雷与这块已挖出的方块相邻，'X' 则表示一个已挖出的地雷。
     * 现在给出在所有未挖出的方块中（'M'或者'E'）的下一个点击位置（行和列索引），根据以下规则，返回相应位置被点击后对应的面板：
     *
     * 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为'X'。
     * 如果一个没有相邻地雷的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的未挖出方块都应该被递归地揭露。
     * 如果一个至少与一个地雷相邻的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
     * 如果在此次点击中，若无更多方块可被揭露，则返回面板。
     * 链接：https://leetcode-cn.com/problems/minesweeper
     */
    public static char[][] updateBoard(char[][] board, int[] click) {
        int rows = board.length;
        int cols = board[0].length;
        int row = click[0];
        int col = click[1];
        if (board[row][col] == 'M') {
            board[row][col] = 'X';
            return board;
        }

        int mCount = getMcount(row, col, rows, cols, board);
        if (mCount > 0) {
            board[row][col] = (char) (mCount + 48);
        } else {
            board[row][col] = 'B';
            visit(row, col, rows, cols, board);
        }
        return board;
    }

    private static void handNextPosition(int row, int col, int rows, int cols, char[][] board) {
        if (row >= 0 && row < rows && col >=0 && col < cols && board[row][col] == 'E') {
            int leftTopMcount = getMcount(row, col, rows, cols, board);
            if (leftTopMcount > 0) {
                board[row][col] = (char) (leftTopMcount + 48);
            } else {
                board[row][col] = 'B';
                Position leftTopPosition = new Position(row, col);
                unvisited.addLast(leftTopPosition);

            }
        }
    }

    private static void visit(int row, int col, int rows, int cols, char[][] board) {

        Position position = new Position(row, col);
        unvisited.addLast(position);
        while (!unvisited.isEmpty()) {
            Position curPosition = unvisited.poll();
            int curRow = (Integer) curPosition.x;
            int curCol = (Integer) curPosition.y;
            // left top
            handNextPosition(curRow - 1, curCol - 1, rows, cols, board);
            // top
            handNextPosition(curRow - 1, curCol, rows, cols, board);
            // right top
            handNextPosition(curRow - 1, curCol + 1, rows, cols, board);
            //right
            handNextPosition(curRow, curCol + 1, rows, cols, board);
            //right down
            handNextPosition(curRow + 1, curCol + 1, rows, cols, board);
            // down
            handNextPosition(curRow + 1, curCol, rows, cols, board);
            //left down
            handNextPosition(curRow + 1, curCol - 1, rows, cols, board);
            //left
            handNextPosition(curRow, curCol - 1, rows, cols, board);
        }
    }

    private static int getMcount(int row, int col, int rows, int cols, char[][] board) {
        int mCount = 0;
        // left top
        if (row - 1 >= 0 && row - 1 < rows && col - 1 >=0 && col - 1 < cols) {
            if (board[row - 1][col - 1] == 'M') {
                mCount ++;
            }
        }
        // top
        if (row - 1 >= 0 && row - 1 < rows && col >=0 && col < cols) {
            if (board[row - 1][col] == 'M') {
                mCount ++;
            }
        }
        // right top
        if (row - 1 >= 0 && row - 1 < rows && col + 1>=0 && col + 1 < cols) {
            if (board[row - 1][col + 1] == 'M') {
                mCount ++;
            }
        }
        //right
        if (row  >= 0 && row < rows && col + 1>=0 && col + 1 < cols) {
            if (board[row][col + 1] == 'M') {
                mCount ++;
            }
        }
        //right down
        if (row + 1 >= 0 && row + 1 < rows && col + 1>=0 && col + 1 < cols) {
            if (board[row + 1][col + 1] == 'M') {
                mCount ++;
            }
        }
        // down
        if (row + 1 >= 0 && row + 1 < rows && col >=0 && col < cols) {
            if (board[row + 1][col] == 'M') {
                mCount ++;
            }
        }
        //left down
        if (row + 1 >= 0 && row + 1 < rows && col - 1>=0 && col - 1 < cols) {
            if (board[row + 1][col - 1] == 'M') {
                mCount ++;
            }
        }
        //left
        if (row >= 0 && row < rows && col - 1>=0 && col - 1 < cols) {
            if (board[row][col - 1] == 'M') {
                mCount ++;
            }
        }
        return mCount;
    }

    /**
     * 最初在一个记事本上只有一个字符 'A'。你每次可以对这个记事本进行两种操作：
     *
     * Copy All (复制全部) : 你可以复制这个记事本中的所有字符(部分的复制是不允许的)。
     * Paste (粘贴) : 你可以粘贴你上一次复制的字符。
     * 给定一个数字 n 。你需要使用最少的操作次数，在记事本中打印出恰好 n 个 'A'。输出能够打印出 n 个 'A' 的最少操作次数。
     *
     * 示例 1:
     *
     * 输入: 3
     * 输出: 3
     * 解释:
     * 最初, 我们只有一个字符 'A'。
     * 第 1 步, 我们使用 Copy All 操作。
     * 第 2 步, 我们使用 Paste 操作来获得 'AA'。
     * 第 3 步, 我们使用 Paste 操作来获得 'AAA'。
     *
     * 链接：https://leetcode-cn.com/problems/2-keys-keyboard
     * @param args
     */
    public static int minSteps(int n) {
        int[] results = new int[n + 1];
        results[0] = 0;
        results[1] = 0;
        for (int i = 2; i <= n; i ++) {
            results[i] = Integer.MAX_VALUE;
        }
        for(int i = 1; i <= n; i ++) {
            // copy
            int copyTime = 1;
            for (int j = i + i * copyTime; j <= n;) {
                if (results[i] + copyTime + 1 <= results[j]) {
                    results[j] = results[i] + copyTime + 1;
                }
                copyTime ++;
                j = i + i * copyTime;
            }
        }
        return results[n];
    }
    public static void main(String[] args) {
//        char[][] board = {
//                {'E', 'E', 'E', 'E', 'E'},
//                {'E', 'E', 'M', 'E', 'E'},
//                {'E', 'E', 'E', 'E', 'E'},
//                {'E', 'E', 'E', 'E', 'E'}
//        };
//        int[] click = new int[]{3,0};
//
//        updateBoard(board, click);
//        int rows = board.length;
//        int cols = board[0].length;
//        for (int i = 0; i < rows; i ++) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int j = 0; j < cols; j ++) {
//                stringBuilder.append(board[i][j] + " ");
//            }
//            System.out.println(stringBuilder.toString());
//        }
        System.out.println(minSteps(6));
    }
}
