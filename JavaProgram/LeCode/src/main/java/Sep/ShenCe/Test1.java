package Sep.ShenCe;

import java.util.*;

public class Test1 {
    private static class Position {
        int leftIndex;
        int rightIndex;

        public Position(int leftIndex, int rightIndex) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }
    }

    public static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Stack<Character> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < input.length(); i ++) {
            char c = input.charAt(i);
            if (c == '(') {
                stack1.push(c);
                stack2.push(i);
                continue;
            }
            if (c == ')') {
                stack1.pop();
                int leftIndex = stack2.pop();
                Position position = new Position(leftIndex, i);
                positions.add(position);
                continue;
            }
        }
        positions.sort(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return o1.leftIndex - o2.leftIndex;
            }
        });
        System.out.println(positions.size());
        for (Position position : positions) {
            System.out.println(position.leftIndex);
            System.out.println(position.rightIndex);
        }
    }

    public static void main(String[] args) {
        Solution();
    }
}
