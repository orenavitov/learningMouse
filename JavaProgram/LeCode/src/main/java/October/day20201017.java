package October;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day20201017 {
    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        dfs(0, 0, s, new StringBuilder(), result);
        return result;
    }

    public static void dfs(int start, int devideTime, String s, StringBuilder temp, List<String> results) {
        if (devideTime == 3) {
            if (start < s.length() && s.length() - start <= 3) {
                temp.append(".");
                String subString = s.substring(start);
                if (Integer.valueOf(subString) > 255 || (subString.length()> 1 && subString.charAt(0) == '0')) {
                    return;
                }
                temp.append(s.substring(start));
                results.add(temp.toString());
            }
            return;
        }
        int end = temp.length();
        for (int i = 1; i <= 3 && start + i < s.length(); i ++) {
            String sub = s.substring(start, start + i);
            if (Integer.valueOf(sub) > 255 || (sub.length()> 1 && sub.charAt(0) == '0')) {
                return;
            }
            if (devideTime != 0) {
                temp.append(".");
            }
            temp.append(sub);
            dfs(start + i, devideTime + 1, s, temp, results);
            temp = new StringBuilder(temp.substring(0, end));
        }
    }

    public static List<String> commonChars(String[] A) {

        List<String> results = new ArrayList<>();
        List<HashMap<Character, Integer>> charAndCountMapList = new ArrayList<>();
        for (int i = 0; i < A.length; i ++) {
            HashMap<Character, Integer> charAndCountMap = new HashMap<>();
            String str = A[i];
            for (int j = 0; j < str.length(); j ++) {
                char c = str.charAt(j);
                charAndCountMap.computeIfAbsent(c, (k) -> {
                    return 0;
                });
                charAndCountMap.computeIfPresent(c, (k, v) -> {
                    return v + 1;
                });
            }
            charAndCountMapList.add(charAndCountMap);
        }
        for (Character c : A[0].toCharArray()) {
            boolean allExist = true;
            for (int i = 1; i < charAndCountMapList.size(); i ++) {
                HashMap<Character, Integer> charAndCountMap = charAndCountMapList.get(i);
                if (!charAndCountMap.containsKey(c) || charAndCountMap.get(c) == 0) {
                    allExist = false;
                } else {
                    charAndCountMap.computeIfPresent(c, (k, v) -> {
                        return v - 1;
                    });
                }
            }
            if (allExist) {
                results.add("" + c);
            }
        }
        return results;
    }

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
        Node temp = new Node();
        Node preTemp = new Node();
        preTemp.next = root;
        while (preTemp.next != null) {
            Node curNode = preTemp.next;
            Node left = curNode.left;
            Node right = curNode.right;
            preTemp = preTemp.next;
            if (left != null) {
                if (preTemp.next == null) {
                    preTemp.next = left;
                }

                temp.next = left;
                temp = left;
            }
            if (right != null) {
                if (preTemp.next == null) {
                    preTemp.next = right;
                }
                temp.next = right;
                temp = right;
            }

        }
        return root;
    }

    public static void main(String[] args) {
        Node node1= new Node(1);
        Node node2= new Node(2);
        Node node3= new Node(3);
        Node node4= new Node(4);
        Node node5= new Node(5);
        Node node6= new Node(6);
        Node node7= new Node(7);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        Node root = connect(node1);
        System.out.println("over!");
    }
}
