package August;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.IntStream;

public class day20200816 {
    /**
     * 有一幅以二维整数数组表示的图画，每一个整数表示该图画的像素值大小，数值在 0 到 65535 之间。
     *
     * 给你一个坐标 (sr, sc) 表示图像渲染开始的像素值（行 ，列）和一个新的颜色值 newColor，让你重新上色这幅图像。
     *
     * 为了完成上色工作，从初始坐标开始，记录初始坐标的上下左右四个方向上像素值与初始坐标相同的相连像素点，接着再记录这四个方向上符合条件的像素点与他们对应四个方向上像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为新的颜色值。
     * 最后返回经过上色渲染后的图像。
     * 示例 1:
     * 输入:
     * image = [[1,1,1],[1,1,0],[1,0,1]]
     * sr = 1, sc = 1, newColor = 2
     * 输出: [[2,2,2],[2,2,0],[2,0,1]]
     * 解析:
     * 在图像的正中间，(坐标(sr,sc)=(1,1)),
     * 在路径上所有符合条件的像素点的颜色都被更改成2。
     * 注意，右下角的像素没有更改为2，
     * 因为它不是在上下左右四个方向上与初始点相连的像素点。
     * 注意:
     *
     * image 和image[0]的长度在范围[1, 50] 内。
     * 给出的初始点将满足0 <= sr < image.length 和0 <= sc < image[0].length。
     * image[i][j] 和newColor表示的颜色值在范围[0, 65535]内。
     * 链接：https://leetcode-cn.com/problems/flood-fill
     */
    private static class Position {
        private int x;
        private int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return this.x;
        }

        int getY() {
            return this.y;
        }



        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position)) {
                return false;
            } else {
                Position other = (Position) obj;
                return this.x == other.getY() && this.y == other.getY();
            }
        }
    }

    private static LinkedList<Position> toVisit = new LinkedList<>();

    private static ArrayList<Position> visited = new ArrayList<>();

    private static int MAXROW = 0;
    private static int MAXCOL = 0;
    private static int OLDCOLOR = 0;

    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        MAXROW = image.length;
        MAXCOL = image[0].length;
        OLDCOLOR = image[sr][sc];
        Position position = new Position(sr, sc);
        toVisit.addLast(position);
        fill(image, position, newColor);
        return image;
    }

    private static boolean checkPosition(int x, int y) {
        return (x >= 0 && x < MAXROW) && (y >= 0 && y < MAXCOL);
    }

    private static void fill(int[][] image, Position position, int newColor) {
        while (!toVisit.isEmpty()) {
            Position curPosition = toVisit.poll();
            int x = curPosition.x;
            int y = curPosition.y;
            image[x][y] = newColor;
            visited.add(position);
            // top
            Position topPosition = new Position(x - 1, y);
            if (checkPosition(x - 1, y) && !visited.contains(topPosition) && image[x - 1][y] == OLDCOLOR) {
                toVisit.addLast(topPosition);
            }
            // down
            Position downPosition = new Position(x + 1, y);
            if (checkPosition(x + 1, y) && !visited.contains(downPosition) && image[x + 1][y] == OLDCOLOR) {
                toVisit.addLast(downPosition);
            }
            //left
            Position leftPosition = new Position(x, y - 1);
            if (checkPosition(x, y - 1) && !visited.contains(leftPosition) && image[x][y - 1] == OLDCOLOR) {
                toVisit.addLast(leftPosition);
            }
            //right
            Position rightPosition = new Position(x, y + 1);
            if (checkPosition(x, y + 1) && !visited.contains(rightPosition) && image[x][y + 1] == OLDCOLOR) {
                toVisit.addLast(rightPosition);
            }
        }
    }


    /**
     * 给定一个仅包含大小写字母和空格' '的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
     * 如果不存在最后一个单词，请返回 0。
     * 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。
     * 示例:
     * 输入: "Hello World"
     * 输出: 5
     * 链接：https://leetcode-cn.com/problems/length-of-last-word
     * @param
     */
    public static int lengthOfLastWord(String s) {
        int result = 0;
        int length = s.length();
        if (length == 0) {
            return result;
        }
        int start = length - 1;
        boolean notStart = true;
        while (start >= 0) {
            char c = s.charAt(start);
            if (c == ' ') {
                if (notStart) {
                    start --;
                    continue;
                } else {
                    break;
                }
            } else {
                result ++;
                notStart = false;
                start --;
            }

        }
        return result;
    }


    public static void main(String[] args) {
//        int[][] image = new int[][]{
//                {0,0,0},
//                {0,0,0}
//
//        };
//        image = floodFill(image, 0, 0, 2);
//        for (int row = 0; row < MAXROW; row ++) {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("[");
//            for (int col = 0; col < MAXCOL; col ++) {
//                stringBuilder.append(image[row][col] + ",");
//            }
//            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]");
//            System.out.println(stringBuilder.toString());
//
//        }
//        System.out.println(lengthOfLastWord("  abs   "));

    }
}
