package Sep.FanRuan;

import java.util.ArrayList;
import java.util.Scanner;

public class Test1 {
    private static class Node {
        int key;
        int val;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private static void Solution() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineDetials = firstLine.split(" ");
        int n = Integer.valueOf(firstLineDetials[0]);
        int[] lengths = new int[n];
        for (int i = 0; i < n; i ++) {
            lengths[i] = Integer.valueOf(firstLineDetials[i + 1]);
        }
        ArrayList<Node> heads = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            String line = scanner.nextLine();
            String[] lineDetials = line.split(" ");
            String headString = lineDetials[0];
            String[] headDetials = headString.split(":");
            Node head = new Node(Integer.valueOf(headDetials[0]), Integer.valueOf(headDetials[1]));
            Node temp = head;
            for (int j = 1; j < lengths[i]; j ++) {
                String str = lineDetials[j];
                String[] nodeDetials = str.split(":");
                Node node = new Node(Integer.valueOf(nodeDetials[0]), Integer.valueOf(nodeDetials[1]));
                temp.next = node;
                temp = node;
            }
            heads.add(head);
        }
        Node head = mergetLists(heads);
        Node temp = head;
        while (temp != null) {
            System.out.print("" + temp.key + ":" + temp.val + " ");
            temp = temp.next;
        }
    }

    private static boolean check(ArrayList<Node> heads) {
        for (int i = 0; i < heads.size(); i ++) {
            if (heads.get(i) == null) {
                return false;
            }
        }
        return true;
    }

    private static Node mergetLists(ArrayList<Node> heads) {
        if (heads.size() == 1) {
            return heads.get(0);
        }
        ArrayList<Node> tempHeads = heads;
        Node newNode = null;
        if (check(tempHeads)) {
            newNode = updateHeads(heads);
        }
        Node tempNewNode = newNode;
        while (check(tempHeads)) {
            Node next = updateHeads(heads);
            tempNewNode.next = next;
            tempNewNode = next;
        }
        ArrayList<Node> newTempHeads = new ArrayList<>();
        for(Node head : tempHeads) {
            if (head != null) {
                newTempHeads.add(head);
            }
        }
        tempNewNode.next = mergetLists(newTempHeads);
        return newNode;
    }

    private static Node updateHeads(ArrayList<Node> heads) {
        ArrayList<Integer> minIndexs = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < heads.size(); i ++) {
            Node head = heads.get(i);
            int key = head.key;
            if (key < min) {
                min = key;
                minIndexs.clear();
                minIndexs.add(i);
                continue;
            }
            if (key == min) {
                minIndexs.add(i);
            }

        }
        int sum = 0;
        for(int i = 0; i < minIndexs.size(); i ++) {
            int index = minIndexs.get(i);
            Node pre = heads.remove(index);
            sum = sum + pre.val;
            Node cur = pre.next;
            heads.add(index, cur);
        }
        Node newNode = new Node(min, sum);
        return newNode;
    }

    public static void main(String[] args) {
        Solution();
    }
}
