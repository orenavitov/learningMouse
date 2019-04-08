import java.util.ArrayList;
import java.util.List;

public class javaTest2 {

    private class Node {
        private int index_x;
        private int index_y;

        public Node(int x, int y) {
            this.index_x = x;
            this.index_y = y;
        }

        public int getIndex_x() {
            return index_x;
        }
        public int getIndex_y() {
            return index_y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node node = (Node) obj;
                if (node.getIndex_x() == index_x && node.getIndex_y() == index_y) {
                    return true;
                }
            }
            return false;
        }

    }
    List<Node> findNextWays(int maxX, int maxY, int currentX, int currentY) {
        List<Node> ways = new ArrayList<Node>();
        if (currentX + 1 <= maxX) {
            Node directXNode = new Node(currentX + 1, currentY);
            ways.add(directXNode);
        }
        if (currentY + 1 <= maxY) {
            Node directYNode = new Node(currentX, currentY + 1);
            ways.add(directYNode);
        }
        return  ways;
    }
    private static int wayCount = 0;
    void findway(int startX, int startY, int endX, int endY) {
        List<Node> ways = findNextWays(endX, endY, startX, startY);
        if (!ways.isEmpty()) {
             for(Node node : ways) {
                findway(node.getIndex_x(), node.getIndex_y(), endX, endY);
             }
        } else {
            wayCount ++;
        }
    }

    public static void main(String args[]) {
        javaTest2 jt2 = new javaTest2();
        jt2.findway(0, 0, 3, 4);
        System.out.println("the result is: " + wayCount);
    }
}
