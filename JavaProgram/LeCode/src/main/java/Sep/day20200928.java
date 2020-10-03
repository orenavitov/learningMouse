package Sep;

import java.util.LinkedList;

public class day20200928 {
    private static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    public static Node connect(Node root) {
        if (root == null) {
            return null;
        }
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.addLast(root);
        while (!nodes.isEmpty()) {
            for (int i = 0; i + 1< nodes.size(); i ++) {
                Node cur = nodes.get(i);
                Node next = nodes.get(i + 1);
                cur.next = next;
            }
            nodes.get(nodes.size() - 1).next = null;
            int stop = nodes.size();
            for (int i = 0; i < stop; i ++) {
                Node first = nodes.removeFirst();
                Node leftChild = first.left;
                Node rightChild = first.right;
                if (leftChild != null) {
                    nodes.addLast(leftChild);
                }
                if (rightChild != null) {
                    nodes.addLast(rightChild);
                }
            }

        }
        return root;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node7 = new Node(7);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.right = node7;
        Node root = connect(node1);
        System.out.println("end");
    }

}
