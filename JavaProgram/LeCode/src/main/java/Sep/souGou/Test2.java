package Sep.souGou;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Test2  {
    private static class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static String rotatePassword (String[] s1, String[] s2) {
        // write code here
        List<Position> zeroPositions = new ArrayList<>();
        int N = s1.length;
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                String line = s1[i];
                char c = line.charAt(j);
                if (c == '0') {
                    zeroPositions.add(new Position(i, j));
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i ++) {
            for (Position position : zeroPositions) {
                int x = position.x;
                int y = position.y;
                char c = s2[x].charAt(y);
                result.append(c);
            }
            change(zeroPositions, N);
            zeroPositions.sort(new Comparator<Position>() {
                @Override
                public int compare(Position o1, Position o2) {
                    if (o1.x != o2.x) {
                        return o1.x - o2.x;
                    } else {
                        return o1.y - o2.y;
                    }
                }
            });
        }
        return result.toString();
    }

    private static void change(List<Position> zeroPositions, int N) {
        for (int i = 0; i < zeroPositions.size(); i ++) {
            Position position = zeroPositions.get(i);
            int x = position.x;
            int y = position.y;

            position.x = y;
            position.y = N - 1 - x;
        }
    }

    public static void main(String[] args) {
        String[] s1 = {"1101","1010","1111","1110"};
        String[] s2 = {"ABCD","EFGH","IJKL","MNPQ"};
        System.out.println(rotatePassword(s1, s2));
    }
}
